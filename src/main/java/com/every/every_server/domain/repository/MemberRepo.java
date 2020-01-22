package com.every.every_server.domain.repository;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.vo.member.MemberPublicVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {
    public Member findByEmailAndPw(String email, String pw);
}