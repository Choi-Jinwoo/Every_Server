package com.every.every_server.service.school;

import com.every.every_server.domain.vo.school.SchoolMealVO;
import com.every.every_server.domain.vo.school.SchoolVO;

import java.io.IOException;
import java.util.List;

public interface SchoolService {
    public List<SchoolVO> getSchoolList(String schoolName) throws Exception;
    public List<SchoolMealVO> getSchoolMeals(Integer idx) throws Exception;
    public SchoolVO getSchoolBySchoolId(String reqSchoolId) throws Exception;
}
