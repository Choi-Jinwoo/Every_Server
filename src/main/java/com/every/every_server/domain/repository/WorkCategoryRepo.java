package com.every.every_server.domain.repository;

import com.every.every_server.domain.entity.WorkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkCategoryRepo extends JpaRepository<WorkCategory, Integer> {
}
