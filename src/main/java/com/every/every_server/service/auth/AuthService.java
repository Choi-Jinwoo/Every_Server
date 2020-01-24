package com.every.every_server.service.auth;

import com.every.every_server.domain.vo.member.MemberLoginVO;
import com.every.every_server.domain.vo.member.StudentRegisterVO;
import com.every.every_server.domain.vo.member.WorkerRegisterVO;

public interface AuthService {
    public Integer login(MemberLoginVO memberLiLoginVO);
    public Integer studentRegister(StudentRegisterVO student);
    public Integer workerRegister(WorkerRegisterVO worker);
}
