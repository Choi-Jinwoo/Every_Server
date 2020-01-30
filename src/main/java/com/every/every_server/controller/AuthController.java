package com.every.every_server.controller;

import com.every.every_server.domain.vo.http.Response;
import com.every.every_server.domain.vo.http.ResponseData;
import com.every.every_server.domain.vo.member.MemberLoginVO;
import com.every.every_server.domain.vo.member.StudentRegisterVO;
import com.every.every_server.domain.vo.member.WorkerRegisterVO;
import com.every.every_server.service.auth.AuthServiceImpl;
import com.every.every_server.service.jwt.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 회원 인증
 */
@CrossOrigin
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
        try {
            Integer memberIdx = authService.login(memberLoginVO);
            String token = jwtService.createToken(memberIdx);

            Map<String, Object> data = new HashMap<>();
            data.put("x-access-token", token);
            return new ResponseData(HttpStatus.OK, "로그인 성공.", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    /**
     * 학생 회원가입 API
     */
    @PostMapping("/register/student")
    @ResponseStatus(HttpStatus.CREATED)
    public Response studentRegister(@RequestBody @Valid StudentRegisterVO studentRegisterVO) {
        try {
            authService.studentRegister(studentRegisterVO);
            return new Response(HttpStatus.CREATED, "회원 가입 성공.");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    /**
     * 직장인 회원가입 API
     */
    @PostMapping("/register/worker")
    @ResponseStatus(HttpStatus.CREATED)
    public Response workerRegister(@RequestBody @Valid WorkerRegisterVO workerRegisterVO) {
        try {
            authService.workerRegister(workerRegisterVO);
            return new Response(HttpStatus.CREATED, "회원 가입 성공.");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }
}
