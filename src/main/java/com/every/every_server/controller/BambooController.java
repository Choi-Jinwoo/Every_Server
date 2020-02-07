package com.every.every_server.controller;

import com.every.every_server.domain.vo.bamboo.post.BambooPostVO;
import com.every.every_server.domain.vo.bamboo.post.BambooWritePostVO;
import com.every.every_server.domain.vo.http.Response;
import com.every.every_server.domain.vo.http.ResponseData;
import com.every.every_server.service.bamboo.BambooServiceImpl;
import com.every.every_server.service.jwt.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/bamboo")
public class BambooController {
    @Autowired
    private BambooServiceImpl bambooService;
    @Autowired
    private JwtServiceImpl jwtService;

    /**
     * 게시글 목록 조회
     */
    @GetMapping("/post")
    public Response getBambooPosts(@RequestHeader String token) {
        List<BambooPostVO> postList = new ArrayList<>();

        try {
            Integer memberIdx = jwtService.validateToken(token);
            postList = bambooService.getBambooPosts(memberIdx);

            Map<String, Object> data = new HashMap<>();
            data.put("posts", postList);
            return new ResponseData(HttpStatus.OK, "대나무숲 목록 조회 성공.", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/post")
    public Response writeBambooPost(
            @RequestHeader String token,
            @RequestBody BambooWritePostVO bambooWritePostVO) {
        try {
            Integer memberIdx = jwtService.validateToken(token);
            bambooService.writeBambooPost(memberIdx, bambooWritePostVO);

            return new Response(HttpStatus.CREATED, "대나무숲 작성 성공.");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }
}
