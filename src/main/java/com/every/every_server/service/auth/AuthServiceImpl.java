package com.every.every_server.service.auth;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.entity.Student;
import com.every.every_server.domain.entity.WorkCategory;
import com.every.every_server.domain.entity.Worker;
import com.every.every_server.domain.repository.MemberRepo;
import com.every.every_server.domain.repository.StudentRepo;
import com.every.every_server.domain.repository.WorkCategoryRepo;
import com.every.every_server.domain.repository.WorkerRepo;
import com.every.every_server.domain.vo.member.MemberLoginVO;
import com.every.every_server.domain.vo.member.StudentRegisterVO;
import com.every.every_server.domain.vo.member.WorkerRegisterVO;
import org.hibernate.action.internal.OrphanRemovalAction;
import org.hibernate.jdbc.Work;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private WorkerRepo workerRepo;
    @Autowired
    private WorkCategoryRepo workCategoryRepo;

    /**
     * 로그인
     * @return 회원 고유 번호
     */
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Integer login(MemberLoginVO memberLoginVO) {
        try {
            Member member = memberRepo.findByEmailAndPw(memberLoginVO.getEmail(), memberLoginVO.getPw());

            // 인증실패(회원 없음)
            if (member == null) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "인증 실패.");
            }

            return member.getIdx();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 학생 회원가입
     * @return 가입회원 고유번호
     */
    @Override
    public Integer studentRegister(StudentRegisterVO student) {
        try {
            validateBirthYear(student.getBirthYear());
            validatekDupEmail(student.getEmail());
            validateDupPhone(student.getPhone());

            // 회원 저장
            ModelMapper modelMapper = new ModelMapper();
            Member memberMappedMember = modelMapper.map(student, Member.class);
            Member registedMember = memberRepo.save(memberMappedMember);

            // 학생 저장
            Student studentMappedStudent = new Student(null, registedMember, student.getSchoolId());
            studentRepo.save(studentMappedStudent);

            return registedMember.getIdx();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 직장인 회원가입
     * @return 가입회원 고유번호
     */
    @Override
    public Integer workerRegister(WorkerRegisterVO worker) {
        try {
            validateBirthYear(worker.getBirthYear());
            validatekDupEmail(worker.getEmail());
            validateDupPhone(worker.getPhone());

            // 근무 분야 확인
            Optional<WorkCategory> workCategory = workCategoryRepo.findById(worker.getWorkCategory());
            if (!workCategory.isPresent()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "근무 분야 없음.");
            }

            // 회원 저장
            ModelMapper modelMapper = new ModelMapper();
            Member memberMappedMember = modelMapper.map(worker, Member.class);
            Member registedMember = memberRepo.save(memberMappedMember);

            // 직장인 저장
            Worker workerMappedMember = new Worker(null, registedMember, worker.getWorkPlace(), workCategory.get());
            workerRepo.save(workerMappedMember);

            return registedMember.getIdx();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 이메일 확인
     * @return email
     */
    @Override
    public String checkEmail(String email) {
        if (email == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.");
        }

        Member member = memberRepo.findByEmail(email);
        if (member != null) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "중복된 이메일.");
        }

        return email;
    }

    @Override
    public String checkPhone(String phone) {
        if (phone == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.");
        }

        Member member = memberRepo.findByPhone(phone);
        if (member != null) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "중복된 전화번호.");
        }

        return phone;
    }

    private boolean validateBirthYear(Integer birthYear) {
        int currentYear = LocalDate.now().getYear();
        if (currentYear < birthYear) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.");
        }

        return true;
    }

    private boolean validatekDupEmail(String email) {
        Member emailDuplicateMember = memberRepo.findByEmail(email);
        if (emailDuplicateMember != null) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "중복된 이메일.");
        }

        return true;
    }

    private boolean validateDupPhone(String phone) {
        Member phoneDuplicateMember = memberRepo.findByPhone(phone);
        if (phoneDuplicateMember != null) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "중복된 전화번호.");
        }

        return  true;
    }
}
