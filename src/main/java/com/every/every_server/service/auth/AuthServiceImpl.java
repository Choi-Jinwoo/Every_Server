package com.every.every_server.service.auth;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.repository.MemberRepo;
import com.every.every_server.domain.vo.member.MemberLoginVO;
import com.every.every_server.domain.vo.member.MemberPublicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private MemberRepo memberRepo;

    /**
     * 로그인
     * @return 회원 고유 번호
     */
    @Override
    public Integer login(MemberLoginVO memberLoginVO) {
        Member member = memberRepo.findByEmailAndPw(memberLoginVO.getEmail(), memberLoginVO.getPw());

        // 인증실패(회원 없음)
        if (member == null) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "인증 실패.");
        }

        return member.getIdx();
    }
}