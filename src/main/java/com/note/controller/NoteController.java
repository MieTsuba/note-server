package com.note.controller;

import com.note.auth.TokenStore;
import com.note.entity.Note;
import com.note.mapper.NoteMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class NoteController {

    private final NoteMapper noteMapper;

    public NoteController(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @GetMapping("/note/list")
    public ResponseEntity<?> list(
            @RequestHeader(value = "Authorization", required = false) String auth   // ← 改 2：加参数
    ) {
        if (auth == null || !auth.startsWith("Bearer ")) {              // ← 改 3：加判断
            return ResponseEntity.status(401).body(Map.of("code", 401, "message", "Unauthorized"));
        }

        String token = auth.substring(7);      // "Bearer " 是 7 个字符，去掉它
        // ★ 中间还差一步：拿 token 去 TOKEN_STORE 查
        Long id = TokenStore.get(token);
        //   查不到 → 401；查到了 → 继续
        if(id == null){
            return ResponseEntity.status(401).body(Map.of("code", 401, "message", "Invalid token"));
        }

        return ResponseEntity.ok(noteMapper.selectList(null));
    }
}
