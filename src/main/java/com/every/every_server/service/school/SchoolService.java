package com.every.every_server.service.school;

import com.every.every_server.domain.vo.school.SchoolVO;

import java.util.List;

public interface SchoolService {
    public List<SchoolVO> getSchoolList(String schoolName);
}
