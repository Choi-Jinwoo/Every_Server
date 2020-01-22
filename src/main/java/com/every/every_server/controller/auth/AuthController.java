package com.every.every_server.controller.auth;

import com.every.every_server.annotation.ApiVersion;
import com.every.every_server.domain.vo.http.Response;
import com.every.every_server.domain.vo.http.ResponseData;
import com.every.every_server.domain.vo.member.MemberLoginVO;
import com.every.every_server.service.auth.AuthServiceImpl;
import com.every.every_server.service.jwt.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 회원 인증
 */
@ApiVersion({ 1 })
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
    public Response login(@RequestBody MemberLoginVO memberLoginVO) {
        Integer memberIdx = authService.login(memberLoginVO);
        String token = jwtService.createToken(memberIdx);

        Map<String, Object> data = new HashMap<>();
        data.put("x-access-token", token);
        return new ResponseData(HttpStatus.OK, "로그인 성공.", data);
    }
}
