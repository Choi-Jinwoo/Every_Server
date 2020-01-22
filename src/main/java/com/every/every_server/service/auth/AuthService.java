package com.every.every_server.service.auth;

import com.every.every_server.domain.vo.member.MemberLoginVO;

public interface AuthService {
    public Integer login(MemberLoginVO memberLiLoginVO);
}
