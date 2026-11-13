package com.note.controller;

import com.note.auth.TokenStore;
import com.note.entity.Note;
import com.note.mapper.NoteMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NoteController {

    private final NoteMapper noteMapper;
    private final TokenStore tokenStore;

    public NoteController(NoteMapper noteMapper, TokenStore tokenStore) {
        this.noteMapper = noteMapper;
        this.tokenStore = tokenStore;
    }

    @GetMapping("/note/list")
    public ResponseEntity<?> list(
            @RequestHeader(value = "Authorization", required = false) String auth
    ) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("code", 401, "message", "Unauthorized"));
        }

        String token = auth.substring(7);
        Long userId = tokenStore.get(token);
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("code", 401, "message", "Invalid token"));
        }

        return ResponseEntity.ok(noteMapper.selectList(null));
    }
}
