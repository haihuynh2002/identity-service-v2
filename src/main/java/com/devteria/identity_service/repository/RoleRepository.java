package com.devteria.identity_service.repository;

import com.devteria.identity_service.entity.Permission;
import com.devteria.identity_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
