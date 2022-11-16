package com.esimed.quizz.listener;

import com.esimed.quizz.services.WebSocketSessionService;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

public class PresenceEventListener {
    private final WebSocketSessionService webSocketSessionService;

    public PresenceEventListener(WebSocketSessionService webSocketSessionService) {
        this.webSocketSessionService = webSocketSessionService;
    }

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        webSocketSessionService.getExisting();
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        webSocketSessionService.clear();
    }
}
