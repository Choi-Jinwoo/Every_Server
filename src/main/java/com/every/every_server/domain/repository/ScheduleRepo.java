package com.every.every_server.domain.repository;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {
    public List<Schedule> findAllByMember(Member member);
}
