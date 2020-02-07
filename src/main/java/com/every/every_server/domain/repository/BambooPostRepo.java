package com.every.every_server.domain.repository;

import com.every.every_server.domain.entity.BambooPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BambooPostRepo extends JpaRepository<BambooPost, Integer> {
}
