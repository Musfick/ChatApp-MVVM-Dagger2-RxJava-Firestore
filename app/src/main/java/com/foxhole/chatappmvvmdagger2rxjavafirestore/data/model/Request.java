package com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model;

public class Request {
    private String requestType;
    private String timeStamp;

    public Request() {
    }

    public Request(String requestType, String timeStamp) {
        this.requestType = requestType;
        this.timeStamp = timeStamp;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
