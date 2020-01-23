package com.every.every_server.domain.vo.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 학교 기본 정보 VO
 */
@Getter
@AllArgsConstructor
public class SchoolVO {
    @JsonProperty("school_id")
    private String schoolId; // 시.도 교육청 ID

    @JsonProperty("office_id")
    private String officeId; // 학교 ID

    @JsonProperty("school_name")
    private String schoolName; // 학교 이름

    @JsonProperty("school_location")
    private String schoolLocation; // 학교 소재지(위치)
}
