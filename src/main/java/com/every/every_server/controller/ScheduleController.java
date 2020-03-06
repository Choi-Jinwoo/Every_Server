package com.every.every_server.controller;

import com.every.every_server.domain.entity.Schedule;
import com.every.every_server.domain.vo.http.Response;
import com.every.every_server.domain.vo.http.ResponseData;
import com.every.every_server.domain.vo.schedule.ScheduleVO;
import com.every.every_server.service.jwt.JwtServiceImpl;
import com.every.every_server.service.schedule.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleServiceImpl scheduleService;
    @Autowired
    private JwtServiceImpl jwtService;

    @GetMapping
    public Response getSchedules(@RequestHeader String token) {
        try {
            Integer memberIdx = jwtService.validateToken(token);
            List<ScheduleVO> schedules = scheduleService.getSchedules(memberIdx);

            Map<String, Object> data = new HashMap<>();
            data.put("schedules", schedules);
            return new ResponseData(HttpStatus.OK, "일정 조회 성공", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }
}
