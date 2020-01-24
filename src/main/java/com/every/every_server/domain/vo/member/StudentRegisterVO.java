package com.every.every_server.domain.vo.member;

import com.every.every_server.constant.DateConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 학생 가입 VO
 */
@Getter
public class StudentRegisterVO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 25)
    private String pw;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @JsonProperty("birth_year")
    @NotNull
    private Integer birthYear;

    @JsonProperty("school_id")
    @NotNull
    private String schoolId;
}
