package com.tp.serviceley.server.model.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisSession {
    private Long userId;
    private String email;
    private String token;
}
