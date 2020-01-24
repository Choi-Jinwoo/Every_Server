package com.every.every_server.service.auth;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.vo.member.MemberLoginVO;
import com.every.every_server.domain.vo.member.MemberRegisterVO;

public interface AuthService {
    public Integer login(MemberLoginVO memberLiLoginVO);
    public Integer register(MemberRegisterVO member);
}
