### 一、简介
  + 说明：activemq消息中间件封装
  
### 二：功能
   + 提供`发送消息`工具类
   + 消息接收者应该只需要添加消息`监听注解`，无需了解实现细节
   + 开启`异步线程消费`消息，防止容器启动时在消费数据，导致主线程阻塞，而无法启动容器
   + 假如消费的消息失败，默认`重连三次`，如果还是失败则放入死信队列，防止其它的消息被动阻塞
   + 如果`消费能力过低`，系统自动开启多个线程进行并行消费
   + 支持广播消息
   + 继续支持从配置中心获取个性化配置
   + 支持消费者与生产者各自维护自己需要的数据(包括但不限于`bean`、`map`、`list`等)
   
### 三：即将添加
   + 支持消息溯源

### 四：注意事项
   + 集群：`JMS`规范的广播消息是只能支持单台机器，不支持集群，所以为了解决集群问题，`active-mq`提供了一个虚拟主题的功能，
        发送者会以广播消息发送，接收者以队列的形式接收，`active-mq`会以消息目的地名称匹配规则自动将广播消费分发到相应的队列中，
        默认以`module-name.destination`(`module-name`是对应的模块名称，如`chen`,`destination`是消息目的地地址，如:`out2job.queue`)
        + 注意：消费者的这个`module-name`是用来区分各个不同模块的消费者以及给`MQ`作为路由分发的，在消息发送端，是不需要填写模块名的
        如：消费者的`destination`为`chen.out2job.queue`，那么发送者的`destination`为`out2job.queue`，如果大家觉得麻烦，可以采用
        `el`表达式，如：`${module-name}.destination`，若：`module-name`为`chen`，那么最后解析出来的`destination`为：`chen.out2job.queue`
   + 事务：`MQ`与`JDBC`本身是属于两个不同级别的连接，业务上无法保证事务的一致性(`spring`尽最大努力保证事务的一致性，如两个不同的连接都会在`spring`
         的事物范围内管理，只有方法执行完毕后，然后会通过`AOP`的方式相继的提交事务)
         但是有些场景是满足不了的如：method b ->call->method b，消息是在b方法发送的，而且b方法使用了新的事务，(如配置`transactional`的
         事务传播属性为`TransactionDefinition.PROPAGATION_REQUIRES_NEW`)这样将导致消费方消费了提供方未提交的数据，这是一个严重的问题
   + 原则上，发送队列消息与广播消息在消费方是同样的处理方式，所以为了保持简单性与可扩展性，此组件不提供发送队列消息，只允许发送广播消息      

### 六：使用方式
##### 消费者
    @CustomJmsListener
    @Component
    public class MessageReceiver {
    
        @JmsListener(destination = "chen.bean")
        public void bean(@CustomMapping Email email) {
            System.out.println("bean <" + email + ">");
        }
    
        @JmsListener(destination = "chen.map")
        public void map(@CustomMapping Map<String, Email2> map) {
            System.out.println(map.getClass() + "map <" + map + ">");
        }
    
        @JmsListener(destination = "chen.list")
        public void list(@CustomMapping List<Email2> list) {
            System.out.println("list thread" + Thread.currentThread().getId() + "list <" + list + ">");
        }
    
        @JmsListener(destination = "chen.text")
        public void text(Message message, Session session, String text) {
            System.out.println("text thread" + Thread.currentThread().getId() + "text <"  + text +">");
        }
    
        @JmsListener(destination = "chen.null")
        public void nullText() {
            System.out.println("--------null-------");
        }
    
##### 消息发送者
    @Component
    public class MessageSend {
    
        public void run() {
            sendBean();
            sendMap();
            sendList();
            sendText();
            sendNull();
        }
    
        public static void sendBean() {
            JmsTemplateUtil.send("bean", new Email("hello", "world"));
        }
    
        public static void sendMap() {
            Map<String, Email> map = new HashMap<>();
            map.put("1", new Email("1", "3"));
            JmsTemplateUtil.send("map", map);
        }
    
        public void sendList() {
            List<Email> list = new ArrayList<>();
            list.add(new Email("1", "2"));
            JmsTemplateUtil.send("list", list);
        }
    
        public void sendNull() {
            JmsTemplateUtil.send("null", null);
        }
    
        public void sendText() {
            JmsTemplateUtil.send("text", "value");
        }

### 7：参数配置
  + 每个消费者必须强制指定`@CustomJmsListener`注解，系统需要利用此标识做特殊扩展处理
  + 默认情况下，每个消息类型都必须一一对应，比如：发送者发送了`Mail`对象，消费者也必须强制指定接收类型为`Mail`。如果有需要双方不互相依赖时，那么可以在参数前面添加`@CustomMapping`注解，以达到各自维护各自的数据，如：发送者发送`Mail`对象，消费者可以选择使用`Mail2`对象接收；内部实现是采用`objectMapper.readValue(body, targetJavaType)`，内部对象属性的类型以及属性名称之间的映射关系可以查看`jackson`文档；当然还包括如：`list<Mail>`与`list<Mail2>`,`Map<Mail>`与`Map<Mail2>`等都是支持，以最大简化双方交互的依赖关系。但是集合等泛型操作仅仅支持一个层级的映射，如：`list<Set<Map<String,String>>>`，这种多层级嵌套的类型不支持，需要自己选择其它实现方式
  + `@JmsListener`注解内部元素说明
    + `destination`：消息队列中，消息目的地`key`，类似旧版本的`subject`(与`subscription`不能同时存在)，
      同时支持`EL`表达式，如：${module_name}-bean.name，最后解析出来为:chen-bean.name，如果大家觉得写模块名称不方便，可以尝试用表达式替代
    + `containerFactory`：消息监听工厂`bean`对象(本组件只有一个监听工厂，可以忽略)
    + `subscription`：广播消息的主题(本组件的消费者全部使用队列，所以不需要)
    + `concurrency`：并发的线程数量(系统默认并发线程数是1-5，如果觉得消费能力非常高，可以把最大线程数改小一点，或者仅允许一个线程在消费；如果业务严重依赖顺序消费，那么最好设置为`1-1`保证只有一个线程在消费)
  + `activemq`的控制台默认监听了队列消费者，而且每个消费者开启一个线程，这样会导致消耗大量的内存以及维护大量的无用的线程，所以本组件选择关闭此功能
        在`conf/activemq.xml`配置文件的`broker`元素加入`advisorySupport="false"`属性，并且在`brokerURL`中添加`jms.watchTopicAdvisories=false`