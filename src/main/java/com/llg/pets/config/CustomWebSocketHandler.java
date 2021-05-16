package com.llg.pets.config;

import com.alibaba.fastjson.JSONObject;
import com.llg.pets.chatRoom.ChatMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomWebSocketHandler extends TextWebSocketHandler implements WebSocketHandler {

    // 用户标识
    private static final String CLIENT_ID = "mchNo";
    private static final String USER_NAME = "userName";
    private static final String NICK_NAME = "nickName";
    private static final SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 在线用户列表
    private static final Map<String, WebSocketSession> users;
    static {
        users = new HashMap<>();
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        System.out.println("成功建立websocket-spring连接");
        String mchNo = getMchNo(session);
        if (StringUtils.isNotEmpty(mchNo)) {
            users.put(mchNo, session);
            sendMessageToAllUsers("系统", getNickName(session) + "已上线");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        users.remove(getMchNo(session));
        sendMessageToAllUsers("系统", getNickName(session) + "已下线");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
//        System.out.println("收到消息：" + message.getPayload());
        JSONObject msgJson = JSONObject.parseObject(message.getPayload());
        String to = msgJson.getString("to");
        String msg = msgJson.getString("msg");
        if("all".equals(to.toLowerCase())) {
            sendMessageToAllUsers(getNickName(session), msg);
        }else {
            sendMessageToUser(to, new TextMessage(getMchNo(session) + ":" +msg));
        }
    }

    public boolean sendMessageToUser(String mchNo, TextMessage message) {
        if (users.get(mchNo) == null)
            return false;
        WebSocketSession session = users.get(mchNo);
        if (!session.isOpen()) {
            return false;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean sendMessageToAllUsers(String from, String msg) {
        boolean allSendSuccess = true;
        Set<String> mchNos = users.keySet();
        WebSocketSession session = null;
        for (String mchNo : mchNos) {
            try {
                session = users.get(mchNo);
                if (session.isOpen()) {
                    session.sendMessage(getMessage(from, "all", msg));
                }else {
                }
            } catch (IOException e) {
                allSendSuccess = false;
            }
        }

        return allSendSuccess;
    }

    private TextMessage getMessage (String from ,String to, String msg) {
        ChatMessage chatMessage = new ChatMessage(users.size(), msg);
        chatMessage.setTo(to);
        chatMessage.setFrom(from);
        chatMessage.setSendTime(SF.format(new Date()));
        return new TextMessage(JSONObject.toJSON(chatMessage).toString());
    }


    private String getUserName(WebSocketSession session){
        try {
            return session.getAttributes().get(USER_NAME).toString();
        } catch (Exception e) {
            return null;
        }
    }
    private String getNickName(WebSocketSession session){
        try {
            return session.getAttributes().get(NICK_NAME).toString();
        } catch (Exception e) {
            return null;
        }
    }
    private String getMchNo(WebSocketSession session) {
        try {
            return session.getAttributes().get(CLIENT_ID).toString();
        } catch (Exception e) {
            return null;
        }
    }
}
