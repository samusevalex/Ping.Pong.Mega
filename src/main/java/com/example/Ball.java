package com.example;

import java.io.Serializable;

public class Ball implements Serializable {

    private String name;
    private int counter;
    private long date;

    public String getName() {
        return name;
    }

    public int getCounter() {
        return counter;
    }

    public long getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "name=" + name +
                ", counter=" + counter +
                ", date=" + date;
    }
}