package com.serviciosRosario.ServiciosRosario.repository;


import com.serviciosRosario.ServiciosRosario.entity.permission.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Integer> {
    Optional<Permission> findByName(String name);
}
