package com.every.every_server.service.member;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.entity.Student;
import com.every.every_server.domain.entity.Worker;
import com.every.every_server.domain.repository.MemberRepo;
import com.every.every_server.domain.repository.StudentRepo;
import com.every.every_server.domain.repository.WorkerRepo;
import com.every.every_server.domain.vo.member.StudentPublicVO;
import org.modelmapper.ModelMapper;
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

    @Override
    public StudentPublicVO getMemberByStudentIdx(Integer studentIdx) {
        Optional<Student> student = studentRepo.findById(studentIdx);
        if (!student.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"학생 없음.");
        }

        Member member = student.get().getMember();

        ModelMapper modelMapper = new ModelMapper();
        StudentPublicVO studentPublicVO = modelMapper.map(member, StudentPublicVO.class);
        studentPublicVO.setSchoolId(student.get().getSchoolId());
        return studentPublicVO;
    }

    @Override
    public StudentPublicVO getMemberByWorkerIdx(Integer workerIdx) {
        Optional<Worker> worker = workerRepo.findById(workerIdx);
        if (!worker.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"직장인 없음.");
        }

        Member member = worker.get().getMember();

        ModelMapper modelMapper = new ModelMapper();
        StudentPublicVO studentPublicVO = modelMapper.map(member, StudentPublicVO.class);
        return studentPublicVO;
    }
}
