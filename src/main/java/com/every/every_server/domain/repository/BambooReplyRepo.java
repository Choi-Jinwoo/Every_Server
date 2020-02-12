package com.every.every_server.domain.repository;

import com.every.every_server.domain.entity.BambooPost;
import com.every.every_server.domain.entity.BambooReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BambooReplyRepo extends JpaRepository<BambooReply, Integer> {
    public List<BambooReply> findAllByBambooPost(BambooPost post);
}
