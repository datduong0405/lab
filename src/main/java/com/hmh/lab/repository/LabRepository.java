package com.hmh.lab.repository;

import com.hmh.lab.entity.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabRepository extends JpaRepository<Laboratory, Long> {
}
