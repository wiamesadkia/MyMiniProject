package com.MyMiniProject.MiniProject.Repositories;

import com.MyMiniProject.MiniProject.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(String roleName);
}
