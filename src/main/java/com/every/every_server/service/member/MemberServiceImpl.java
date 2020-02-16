package com.every.every_server.service.member;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.entity.Student;
import com.every.every_server.domain.entity.Worker;
import com.every.every_server.domain.repository.MemberRepo;
import com.every.every_server.domain.repository.StudentRepo;
import com.every.every_server.domain.repository.WorkerRepo;
import com.every.every_server.domain.vo.member.MemberPublicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private WorkerRepo workerRepo;

    @Override
    public Student getStudentByMemberIdx(Integer memberIdx) {
        Member member;
        try {
            member = getMember(memberIdx);
        } catch (NullPointerException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "없는 회원.");
        }

        Student student = studentRepo.findByMember(member);

        return student;
    }

    @Override
    public Worker getWorkerByMemberIdx(Integer memberIdx) {
        Member member;
        try {
            member = getMember(memberIdx);
        } catch (NullPointerException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "없는 회원.");
        }

        Worker worker = workerRepo.findByMember(member);

        return worker;
    }

    @Override
    public Member getMember(Integer memberIdx) {
        Optional<Member> member = memberRepo.findById(memberIdx);
        if (!member.isPresent()) {
            throw new NullPointerException();
        }

        return member.get();
    }
}
