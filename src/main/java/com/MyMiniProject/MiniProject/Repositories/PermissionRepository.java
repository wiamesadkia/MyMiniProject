package com.MyMiniProject.MiniProject.Repositories;

import com.MyMiniProject.MiniProject.Entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    Optional<Permission> findByNamePermission(String namePermission);
}
