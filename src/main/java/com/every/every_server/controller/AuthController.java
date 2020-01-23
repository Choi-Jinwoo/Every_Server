package com.every.every_server.controller;

import com.every.every_server.domain.vo.http.Response;
import com.every.every_server.domain.vo.http.ResponseData;
import com.every.every_server.domain.vo.member.MemberLoginVO;
import com.every.every_server.service.auth.AuthServiceImpl;
import com.every.every_server.service.jwt.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 회원 인증
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private JwtServiceImpl jwtService;

    /**
     * 회원 로그인 API
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Response login(@RequestBody @Valid MemberLoginVO memberLoginVO) {
        Integer memberIdx = authService.login(memberLoginVO);
        String token = jwtService.createToken(memberIdx);

        Map<String, Object> data = new HashMap<>();
        data.put("x-access-token", token);
        return new ResponseData(HttpStatus.OK, "로그인 성공.", data);
    }
}
