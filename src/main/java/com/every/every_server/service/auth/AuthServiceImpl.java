package com.every.every_server.service.auth;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.repository.MemberRepo;
import com.every.every_server.domain.vo.member.MemberLoginVO;
import com.every.every_server.domain.vo.member.MemberRegisterVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private MemberRepo memberRepo;

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

    @Override
    public Integer register(MemberRegisterVO member) {
        int currentYear = LocalDate.now().getYear();

        try {
            // 출생 연도 검증 오류
            if (currentYear < member.getBirthYear()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.");
            }

            // 중복된 Email 확인
            Member emailDuplicateMember = memberRepo.findByEmail(member.getEmail());
            if (emailDuplicateMember != null) {
                throw new HttpClientErrorException(HttpStatus.CONFLICT, "중복된 이메일.");
            }

            // 중복된 Phone 확인
            Member phoneDuplicateMember = memberRepo.findByPhone(member.getPhone());
            if (phoneDuplicateMember != null) {
                throw new HttpClientErrorException(HttpStatus.CONFLICT, "중복된 전화번호.");
            }

            ModelMapper modelMapper = new ModelMapper();
            Member memberMappedMember = modelMapper.map(member, Member.class);
            Member registedMember = memberRepo.save(memberMappedMember);
            return registedMember.getIdx();
        } catch (Exception e) {
            throw e;
        }
    }
}
