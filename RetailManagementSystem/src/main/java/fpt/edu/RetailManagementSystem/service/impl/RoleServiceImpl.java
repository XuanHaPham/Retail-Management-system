package fpt.edu.RetailManagementSystem.service.impl;
import fpt.edu.RetailManagementSystem.persistent.entity.Role;
import fpt.edu.RetailManagementSystem.persistent.repository.RoleRepository;
import fpt.edu.RetailManagementSystem.service.RoleService;
import fpt.edu.RetailManagementSystem.service.dto.RoleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> getAll(){
        List<Role> roles = roleRepository.getAllRole();
        List<RoleDTO> roleDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Role r : roles ) {
            roleDTOS.add(modelMapper.map(r, RoleDTO.class));
        }
        return roleDTOS;
    }

    @Override
    public Boolean delete(Integer id){
        Optional.ofNullable(roleRepository.findById(id)).orElseThrow(() ->new EntityNotFoundException());
        roleRepository.deleteByID(id);
        return true;
    }

    @Override
    public RoleDTO insert(RoleDTO roleDTO){
        ModelMapper modelMapper = new ModelMapper();
        Role role = modelMapper.map(roleDTO, Role.class);
        role.setStatus(true);
        role = roleRepository.save(role);
        RoleDTO dto = modelMapper.map(role, RoleDTO.class);
        return dto;
    }
}
