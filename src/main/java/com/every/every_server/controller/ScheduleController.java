package com.every.every_server.controller;

import com.every.every_server.domain.entity.Schedule;
import com.every.every_server.domain.vo.http.Response;
import com.every.every_server.domain.vo.http.ResponseData;
import com.every.every_server.domain.vo.schedule.ScheduleCreateVO;
import com.every.every_server.domain.vo.schedule.ScheduleModifyVO;
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

    @GetMapping("/{idx}")
    public Response getSchedule(
            @RequestHeader String token,
            @PathVariable("idx") Integer idx) {
        try {
            Integer memberIdx = jwtService.validateToken(token);
            ScheduleVO schedule = scheduleService.getSchedule(memberIdx, idx);

            Map<String, Object> data = new HashMap<>();
            data.put("schedule", schedule);
            return new ResponseData(HttpStatus.OK, "일정 조회 성공", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

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

    @PostMapping
    public Response createSchedule(
            @RequestHeader String token,
            @RequestBody ScheduleCreateVO scheduleCreateVO) {
        try {
            Integer memberIdx = jwtService.validateToken(token);
            scheduleService.createSchedule(memberIdx, scheduleCreateVO);

            return new Response(HttpStatus.OK, " 일정 생성 성공.");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @PutMapping("/{idx}")
    public Response modifySchedule(
            @RequestHeader String token,
            @PathVariable("idx") Integer idx,
            @RequestBody ScheduleModifyVO scheduleModifyVO) {
        try {
            Integer memberIdx = jwtService.validateToken(token);
            scheduleService.modifySchedule(memberIdx, idx, scheduleModifyVO);

            return new Response(HttpStatus.OK, " 일정 수정 성공.");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @DeleteMapping("/{idx}")
    public Response deleteSchedule(
            @RequestHeader String token,
            @PathVariable("idx") Integer idx) {
        try {
            Integer memberIdx = jwtService.validateToken(token);
            scheduleService.deleteSchedule(memberIdx, idx);

            return new Response(HttpStatus.OK, " 일정 삭제 성공.");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }
}
