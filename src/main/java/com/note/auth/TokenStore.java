package com.note.auth;

import java.util.HashMap;
import java.util.Map;

public class TokenStore {

    private static final Map<String, Long> STORE = new HashMap<>();

    private TokenStore() { }

    public static void put(String token, Long userId) {
        STORE.put(token, userId);
    }

    public static Long get(String token) {
        return STORE.get(token);
    }
}
