package com.serviciosRosario.ServiciosRosario.service.permission;

import com.serviciosRosario.ServiciosRosario.entity.permission.Permission;

import java.util.List;

public interface IPermissionService {
    List<Permission> getAll();
    Permission getById(Integer id);
    Permission create(Permission permission);
    Permission update(Permission permission);
    void delete(Permission permission);
    boolean existsById(Integer id);
}
