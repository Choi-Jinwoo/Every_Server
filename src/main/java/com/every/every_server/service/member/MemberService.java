package com.every.every_server.service.member;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.entity.Student;
import com.every.every_server.domain.entity.Worker;
import com.every.every_server.domain.vo.member.StudentPublicVO;

public interface MemberService {
    public Student getStudentByMemberIdx(Integer memberIdx);
    public Worker getWorkerByMemberIdx(Integer memberIdx);
    public Member getMember(Integer memberIdx);
    public StudentPublicVO getMemberByStudentIdx(Integer studentIdx);
    public StudentPublicVO getMemberByWorkerIdx(Integer workerIdx);
}

