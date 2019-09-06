package fpt.edu.RetailManagementSystem.service;

import fpt.edu.RetailManagementSystem.service.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAll();

    Boolean delete(Integer id);

    RoleDTO insert(RoleDTO roleDTO);
}
