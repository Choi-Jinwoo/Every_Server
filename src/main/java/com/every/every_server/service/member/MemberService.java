package com.every.every_server.service.member;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.entity.Student;
import com.every.every_server.domain.entity.Worker;
import com.every.every_server.domain.vo.member.MemberPublicVO;

public interface MemberService {
    public Student getStudentByMemberIdx(Integer memberIdx);
    public Worker getWorkerByMemberIdx(Integer memberIdx);
    public Member getMember(Integer memberIdx);
    public MemberPublicVO getMemberByStudentIdx(Integer studentIdx);
    public MemberPublicVO getMemberByWorkerIdx(Integer workerIdx);
}

