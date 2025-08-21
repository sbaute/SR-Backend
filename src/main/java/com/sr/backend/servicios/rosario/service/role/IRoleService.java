package com.serviciosRosario.ServiciosRosario.service.role;


import com.serviciosRosario.ServiciosRosario.entity.role.Role;

import java.util.List;

public interface IRoleService {

    List<Role> getAll();
    Role getById(Integer id);
    Role create(Role role);
    Role update(Role role);
    void delete(Role role);
    boolean existsById(Integer id);
}
