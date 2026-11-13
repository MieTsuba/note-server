package com.note.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.note.auth.TokenStore;
import com.note.entity.User;
import com.note.mapper.UserMapper;
import com.note.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    private final UserMapper userMapper;
    private final TokenStore tokenStore;

    public UserController(UserMapper userMapper, TokenStore tokenStore) {
        this.userMapper = userMapper;
        this.tokenStore = tokenStore;
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", req.getUsername());
        User user = userMapper.selectOne(queryWrapper);

        System.out.println("[login] username=" + req.getUsername() + " found=" + (user != null));

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("code", 401, "message", "Invalid username or password"));
        }
        if (!user.getPassword().equals(req.getPassword())) {
            // 和上面那句一字不差：不泄露"用户名是否存在"
            return ResponseEntity.status(401).body(Map.of("code", 401, "message", "Invalid username or password"));
        }

        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user.getId());
        return ResponseEntity.ok(Map.of("code", 200, "token", token));
    }

    @PostMapping("/user/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization", required = false) String auth
    ) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("code", 401, "message", "Unauthorized"));
        }
        String token = auth.substring(7);
        tokenStore.remove(token);
        return ResponseEntity.ok(Map.of("code", 200, "message", "Logged out"));
    }
}
