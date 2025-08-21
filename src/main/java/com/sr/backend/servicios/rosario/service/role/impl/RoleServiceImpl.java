package com.serviciosRosario.ServiciosRosario.service.role.impl;

import com.serviciosRosario.ServiciosRosario.entity.role.Role;
import com.serviciosRosario.ServiciosRosario.repository.RoleRepository;
import com.serviciosRosario.ServiciosRosario.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return List.of();
    }

    @Override
    public Role getById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public boolean existsById(Integer id) {
        return roleRepository.existsById(id);
    }
}
