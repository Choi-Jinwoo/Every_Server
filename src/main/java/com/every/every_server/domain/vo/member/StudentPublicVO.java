package com.every.every_server.domain.vo.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 공개 정보 VO
 */
@Getter
@Setter
public class StudentPublicVO {
    private Integer idx;
    private String email;
    private String name;
    private String phone;
    @JsonProperty("birth_year")
    private Integer birthYear;
    @JsonProperty("school_id")
    private String schoolId;
}
