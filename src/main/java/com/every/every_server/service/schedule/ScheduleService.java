package com.every.every_server.service.schedule;

import com.every.every_server.domain.entity.Schedule;
import com.every.every_server.domain.vo.schedule.ScheduleVO;

import java.util.List;

public interface ScheduleService {
    public List<ScheduleVO> getSchedules(Integer memberIdx);
}
