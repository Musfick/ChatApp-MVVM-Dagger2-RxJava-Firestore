package com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model;

public class Message {

    private String messageType;
    private String message;
    private String imageUrl;
    private String senderUid;

    public Message() {
    }

    public Message(String messageType, String message, String imageUrl) {
        this.messageType = messageType;
        this.message = message;
        this.imageUrl = imageUrl;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }
}
