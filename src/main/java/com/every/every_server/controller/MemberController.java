package com.every.every_server.controller;

import com.every.every_server.domain.vo.http.Response;
import com.every.every_server.domain.vo.http.ResponseData;
import com.every.every_server.domain.vo.member.StudentPublicVO;
import com.every.every_server.service.jwt.JwtServiceImpl;
import com.every.every_server.service.member.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private JwtServiceImpl jwtService;

    /**
     * 학생 IDX -> 회원
     */
    @GetMapping("/student/{idx}")
    public Response getMemberByStudent(
            @RequestHeader String token,
            @PathVariable("idx") Integer idx) {

        try {
            jwtService.validateToken(token);
            StudentPublicVO member = memberService.getMemberByStudentIdx(idx);

            Map<String, Object> data = new HashMap<>();
            data.put("member", member);
            return new ResponseData(HttpStatus.OK, "학생 회원 조회 성공.", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }
}
