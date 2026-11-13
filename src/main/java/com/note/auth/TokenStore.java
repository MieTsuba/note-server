package com.note.auth;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenStore {

    private final Map<String, Long> store = new HashMap<>();

    public void put(String token, Long userId) {
        store.put(token, userId);
    }

    public Long get(String token) {
        return store.get(token);
    }

    public void remove(String token) {
        store.remove(token);
    }
}
