package com.hmh.lab.repository;

import com.hmh.lab.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNameEqualsIgnoreCase(String name);
}
