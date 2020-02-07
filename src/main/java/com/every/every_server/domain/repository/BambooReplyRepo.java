package com.every.every_server.domain.repository;

import com.every.every_server.domain.entity.BambooReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BambooReplyRepo extends JpaRepository<BambooReply, Integer> {
}
