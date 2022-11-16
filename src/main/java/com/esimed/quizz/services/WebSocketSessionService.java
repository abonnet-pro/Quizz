package com.esimed.quizz.services;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionService {

    private List<String> sessionMap = new ArrayList<>();

    public void setUserSession(String userName) {
        sessionMap.add(userName);
    }

    public void removeSession(String userName) {
        sessionMap.remove(userName);
    }

    public List<String> getExisting() {
        return sessionMap;
    }

    public void clear() {
        sessionMap = new ArrayList<>();
    }
}
