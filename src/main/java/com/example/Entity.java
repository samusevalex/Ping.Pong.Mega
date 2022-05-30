package com.example;

public class Entity {

    private String name;
    private int initBall;
    private int counter;
    private long clientDate;
    private long serverDate;

    public void setName(String name) {
        this.name = name;
    }

    public void setInitBall(int initBall) {
        this.initBall = initBall;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setClientDate(long clientDate) {
        this.clientDate = clientDate;
    }

    public void setServerDate(long serverDate) {
        this.serverDate = serverDate;
    }

    public String getName() {
        return name;
    }

    public int getInitBall() {
        return initBall;
    }

    public int getCounter() {
        return counter;
    }

    public long getClientDate() {
        return clientDate;
    }

    public long getServerDate() {
        return serverDate;
    }

    @Override
    public String toString() {
        return "name=" + name +
                ", initBall=" + initBall +
                ", counter=" + counter +
                ", client=" + clientDate +
                ", server=" + serverDate;
    }
}
