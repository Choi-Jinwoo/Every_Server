package com.every.every_server.domain.repository;

import com.every.every_server.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {
    public Member findByEmailAndPw(String email, String pw);

    public Member findByEmail(String email);

    public Member findByPhone(String phone);
}
