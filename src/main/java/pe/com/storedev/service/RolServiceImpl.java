package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.storedev.dto.rol.RoleCreateDTO;
import pe.com.storedev.dto.rol.RoleDTO;
import pe.com.storedev.dto.rol.RolePermissionsDTO;
import pe.com.storedev.dto.rol.RoleUpdateDTO;
import pe.com.storedev.entity.Permission;
import pe.com.storedev.entity.Role;
import pe.com.storedev.exception.NotFoundException;
import pe.com.storedev.mapper.RoleMapper;
import pe.com.storedev.repository.PermissionRepository;
import pe.com.storedev.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDTO> findAllActive() {
        return roleRepository.findAllActiveNotDeleted()
                .stream().map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> findAll(Pageable pageable) {
        return roleRepository.findAllNotDeleted(pageable)
                .map(roleMapper::toDTO);
    }

    @Override
    public RoleDTO create(RoleCreateDTO roleCreateDTO) {
        Role role = roleMapper.toEntity(roleCreateDTO);
        Role saved = roleRepository.save(role);
        return roleMapper.toDTO(saved);
    }

    @Override
    public RoleDTO update(Long id, RoleUpdateDTO roleUpdateDTO) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found with ID: " + id));

        roleMapper.updateEntity(existing, roleUpdateDTO);
        Role updated = roleRepository.save(existing);
        return roleMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO findById(Long id) {
        Role role = roleRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Role not found with ID: " + id));
        return roleMapper.toDTO(role);
    }

    @Override
    public void delete(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found with ID: " + id));

        role.setDeleted(Boolean.TRUE);
        roleRepository.save(role);
    }

    @Override
    public RolePermissionsDTO findRoleWithAllPermissions(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found with ID: " + id));

        return roleMapper.toRolePermissionsDTO(role, permissionRepository.findAll());
    }

    @Override
    @Transactional
    public void assignPermissionToRole(Long roleId, Long permissionId, Boolean assigned) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found"));

        if (assigned) role.getPermissions().add(permission);
        else role.getPermissions().remove(permission);

        roleRepository.save(role);
    }
}
