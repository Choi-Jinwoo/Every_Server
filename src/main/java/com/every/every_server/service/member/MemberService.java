package com.every.every_server.service.member;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.entity.Student;
import com.every.every_server.domain.entity.Worker;
import org.omg.CORBA.INTERNAL;

public interface MemberService {
    public Student getStudentByMemberIdx(Integer memberIdx);

    public Worker getWorkerByMemberIdx(Integer memberIdx);

    public Member getMember(Integer memberIdx);
}

