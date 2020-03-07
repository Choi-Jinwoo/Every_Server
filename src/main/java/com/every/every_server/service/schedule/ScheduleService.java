package com.every.every_server.service.schedule;

import com.every.every_server.domain.vo.schedule.ScheduleCreateVO;
import com.every.every_server.domain.vo.schedule.ScheduleModifyVO;
import com.every.every_server.domain.vo.schedule.ScheduleVO;

import java.util.List;

public interface ScheduleService {
    public List<ScheduleVO> getSchedules(Integer memberIdx);
    public Boolean createSchedule(Integer memberIdx, ScheduleCreateVO scheduleCreateVO);
    public Boolean modifySchedule(Integer memberIdx, Integer idx, ScheduleModifyVO scheduleModifyVO);
}
