package com.every.every_server.controller;

import com.every.every_server.domain.vo.http.Response;
import com.every.every_server.domain.vo.http.ResponseData;
import com.every.every_server.domain.vo.school.SchoolMealVO;
import com.every.every_server.domain.vo.school.SchoolVO;
import com.every.every_server.service.jwt.JwtServiceImpl;
import com.every.every_server.service.school.SchoolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 학교
 */
@CrossOrigin
@RestController
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    private SchoolServiceImpl schoolService;
    @Autowired
    private JwtServiceImpl jwtService;

    /**
     * 학교 조회 API
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Response getSchoolList(@RequestParam String query) {
        List<SchoolVO> schoolList = new ArrayList<>();

        try {
            schoolList = schoolService.getSchoolList(query);

            Map<String, Object> data = new HashMap<>();
            data.put("schools", schoolList);
            return new ResponseData(HttpStatus.OK, "학교 목록 조회 성공.", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @GetMapping("/meal")
    @ResponseStatus(HttpStatus.OK)
    public Response getSchoolMeals(@RequestHeader String token) throws Exception {
        List<SchoolMealVO> mealList;

        try {
            Integer memberIdx = jwtService.validateToken(token);
            mealList = schoolService.getSchoolMeals(memberIdx);

            Map<String, Object> data = new HashMap<>();
            data.put("meals", mealList);
            return new ResponseData(HttpStatus.OK, "급식 목록 조회 성공.", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }
}
