package com.every.every_server.domain.vo.member;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 회원 로그인 VO
 */
@Getter
public class MemberLoginVO {
    @Email
    private String email;

    @NotBlank
    private String pw;
}
