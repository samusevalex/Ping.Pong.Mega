package com.example;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class Settings {

    static int APP_TCP_PORT = 8023;
    static int HTTP_TCP_PORT = 8080;
    static int MAX_BALL_COUNTER = 100;
    static int PAGE_REFRESH_TIME = 1;
    static String ERROR_MESSAGE = "Ошибка";

    @XmlElement(name = "APP_TCP_PORT")
    public void set_APP_TCP_PORT(int APP_TCP_PORT) {
        this.APP_TCP_PORT = APP_TCP_PORT;
    }

    @XmlElement(name = "HTTP_TCP_PORT")
    public void set_HTTP_TCP_PORT(int HTTP_TCP_PORT) {
        this.HTTP_TCP_PORT = HTTP_TCP_PORT;
    }

    @XmlElement(name = "MAX_BALL_COUNTER")
    public void set_MAX_BALL_COUNTER(int MAX_BALL_COUNTER) {
        this.MAX_BALL_COUNTER = MAX_BALL_COUNTER;
    }

    @XmlElement(name = "PAGE_REFRESH_TIME")
    public void set_PAGE_REFRESH_TIME(int PAGE_REFRESH_TIME) {
        this.PAGE_REFRESH_TIME = PAGE_REFRESH_TIME;
    }

    @XmlElement(name = "ERROR_MESSAGE")
    public void set_ERROR_MESSAGE(String ERROR_MESSAGE) {
        this.ERROR_MESSAGE = ERROR_MESSAGE;
    }
}
