package com.every.every_server.service.schedule;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.entity.Schedule;
import com.every.every_server.domain.repository.ScheduleRepo;
import com.every.every_server.domain.vo.schedule.ScheduleCreateVO;
import com.every.every_server.domain.vo.schedule.ScheduleModifyVO;
import com.every.every_server.domain.vo.schedule.ScheduleVO;
import com.every.every_server.service.member.MemberServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private MemberServiceImpl memberService;

    @Override
    public List<ScheduleVO> getSchedules(Integer memberIdx) {
        Member member = memberService.getMember(memberIdx);
        if (member == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "회원 없음.");
        }
        List<Schedule> rawSchedules = scheduleRepo.findAllByMember(member);
        List<ScheduleVO> schedules = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        for (int i = 0; i < rawSchedules.size(); i++) {
            ScheduleVO schedule = modelMapper.map(rawSchedules.get(i), ScheduleVO.class);
            schedule.setStartDate(rawSchedules.get(i).getStartDate().toString());
            schedule.setEndDate(rawSchedules.get(i).getEndDate().toString());
            schedules.add(schedule);
        }
        return schedules;
    }

    @Override
    public Boolean createSchedule(Integer memberIdx, ScheduleCreateVO scheduleCreateVO) {
        Member member = memberService.getMember(memberIdx);
        if (member == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "회원 없음.");
        }

        ModelMapper modelMapper = new ModelMapper();
        Schedule schedule = modelMapper.map(scheduleCreateVO, Schedule.class);
        schedule.setMember(member);
        scheduleRepo.save(schedule);
        return true;
    }

    @Override
    public Boolean modifySchedule(Integer memberIdx, Integer idx, ScheduleModifyVO scheduleModifyVO) {

        Member member = memberService.getMember(memberIdx);
        if (member == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "회원 없음.");
        }

        Optional<Schedule> schedule = scheduleRepo.findById(idx);
        if (!schedule.isPresent()) {
            throw  new HttpClientErrorException(HttpStatus.NOT_FOUND, "일정 없음");
        }

        schedule.get().setTitle(scheduleModifyVO.getTitle() != null ? scheduleModifyVO.getTitle() : schedule.get().getTitle());
        schedule.get().setContent(scheduleModifyVO.getContent() != null ? scheduleModifyVO.getContent() : schedule.get().getContent());
        schedule.get().setStartDate(scheduleModifyVO.getStartDate() != null ? scheduleModifyVO.getStartDate() : schedule.get().getStartDate());
        schedule.get().setEndDate(scheduleModifyVO.getEndDate() != null ? scheduleModifyVO.getEndDate() : schedule.get().getEndDate());

        scheduleRepo.save(schedule.get());
        return true;
    }
}
