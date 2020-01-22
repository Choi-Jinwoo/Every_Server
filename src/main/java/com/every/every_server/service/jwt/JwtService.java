package com.every.every_server.service.jwt;

import com.every.every_server.domain.entity.Member;

public interface JwtService {
    public String createToken(Integer idx);
    public Integer validateToken(String token);
}
