package com.serviciosRosario.ServiciosRosario.service.permission.impl;

import com.serviciosRosario.ServiciosRosario.entity.permission.Permission;
import com.serviciosRosario.ServiciosRosario.repository.PermissionRepository;
import com.serviciosRosario.ServiciosRosario.service.permission.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> getAll() {
        return List.of();
    }

    @Override
    public Permission getById(Integer id) {
        return permissionRepository.findById(id).orElse(null);
    }

    @Override
    public Permission create(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public void delete(Permission permission) {
        permissionRepository.delete(permission);
    }

    @Override
    public boolean existsById(Integer id) {
        return permissionRepository.existsById(id);
    }
}
