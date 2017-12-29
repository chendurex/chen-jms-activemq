package com.chen.jms.domain;

public class Email2 {

    private String to;
    private String body;

    public Email2() {
    }

    public Email2(String to, String body) {
        this.to = to;
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("Email2{to=%s, body=%s}", getTo(), getBody());
    }

}
