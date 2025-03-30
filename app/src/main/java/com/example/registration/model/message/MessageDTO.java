package com.example.registration.model.message;

public class MessageDTO {
    private int customId;
    private int senderId;
    private String text;
    private String creationMessageTime;
    private String role;

    public MessageDTO(int customId, int senderId, String text, String creationMessageTime, String role) {
        this.customId = customId;
        this.senderId = senderId;
        this.text = text;
        this.creationMessageTime = creationMessageTime;
        this.role = role;
    }

    public MessageDTO(int customId, int senderId, String role, String text) {
        this.customId = customId;
        this.senderId = senderId;
        this.role = role;
        this.text = text;
    }

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreationMessageTime() {
        return creationMessageTime;
    }

    public void setCreationMessageTime(String creationMessageTime) {
        this.creationMessageTime = creationMessageTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
