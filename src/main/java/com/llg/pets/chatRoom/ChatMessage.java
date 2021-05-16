package com.llg.pets.chatRoom;


import lombok.Data;

@Data
public class ChatMessage {

    private int onlineCount;
    private String message;
    private String from;
    private String to;
    private String sendTime;

    public ChatMessage(){

    }
    public ChatMessage(int onlineCount, String message){
        this.onlineCount = onlineCount;
        this.message = message;
    }
}
