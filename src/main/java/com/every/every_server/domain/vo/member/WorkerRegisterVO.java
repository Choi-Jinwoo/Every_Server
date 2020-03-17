package com.every.every_server.domain.vo.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 직장인 가입 VO
 */
@Getter
public class WorkerRegisterVO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String pw;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @JsonProperty("birth_year")
    @NotNull
    private Integer birthYear;

    @JsonProperty("work_place")
    private String workPlace;

    @NotNull
    @JsonProperty("work_category")
    private Integer workCategory;
}
