package com.every.every_server.domain.vo.member;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 공개 정보 VO
 */
@Getter
@Setter
public class MemberPublicVO {
    private Integer idx;
    private String email;
    private String name;
}
