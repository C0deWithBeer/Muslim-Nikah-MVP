package com.nikahtech.muslimnikah.models;

import com.nikahtech.muslimnikah.enums.MessageStatus;
import com.nikahtech.muslimnikah.enums.MessageType;

import java.time.Instant;

public class Chat {
    String profilePic;
    String name;
    String lastMessage;
    Instant lastMessageTime;
    MessageStatus lastMessageStatus;
    MessageType lastMessageType;
    String lastMessageMeta;
    Integer unreadMessageCount;


    public Chat() {
    }

    public Chat(String profilePic, String name, String lastMessage, Integer unreadMessageCount) {
        this.profilePic = profilePic;
        this.name = name;
        this.lastMessage = lastMessage;
        this.unreadMessageCount = unreadMessageCount;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Instant getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Instant lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public MessageStatus getLastMessageStatus() {
        return lastMessageStatus;
    }

    public void setLastMessageStatus(MessageStatus lastMessageStatus) {
        this.lastMessageStatus = lastMessageStatus;
    }

    public MessageType getLastMessageType() {
        return lastMessageType;
    }

    public void setLastMessageType(MessageType lastMessageType) {
        this.lastMessageType = lastMessageType;
    }

    public String getLastMessageMeta() {
        return lastMessageMeta;
    }

    public void setLastMessageMeta(String lastMessageMeta) {
        this.lastMessageMeta = lastMessageMeta;
    }

    public Integer getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(Integer unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }
}
