package com.project.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static Map<Long, List<WebSocketSession>> projectSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long projektId = extractProjectId(session);

        List<WebSocketSession> sessions = projectSessions.getOrDefault(projektId, new ArrayList<>());
        sessions.add(session);
        projectSessions.put(projektId, sessions);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long projektId = extractProjectId(session);
        List<WebSocketSession> sessions = projectSessions.get(projektId);

        if (sessions != null) {
            for (WebSocketSession webSocketSession : sessions) {
                try {
                    webSocketSession.sendMessage(new TextMessage(message.getPayload()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long projektId = extractProjectId(session);
        List<WebSocketSession> sessions = projectSessions.get(projektId);

        if (sessions != null) {
            sessions.remove(session);
        }
    }

    private Long extractProjectId(WebSocketSession session) {
        String uri = session.getUri().toString();
        String[] parts = uri.split("/");
        String projectIdStr = parts[parts.length - 1];
        return Long.parseLong(projectIdStr);
    }
}
