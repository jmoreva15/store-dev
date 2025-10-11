package pe.com.storedev.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.storedev.dto.rol.RoleCreateDTO;
import pe.com.storedev.dto.rol.RoleDTO;
import pe.com.storedev.dto.rol.RolePermissionsDTO;
import pe.com.storedev.dto.rol.RoleUpdateDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> findAllActive();
    Page<RoleDTO> findAll(Pageable pageable);
    RoleDTO create(RoleCreateDTO roleCreateDTO);
    RoleDTO update(Long id, RoleUpdateDTO roleUpdateDTO);
    RoleDTO findById(Long id);
    void delete(Long id);
    RolePermissionsDTO findRoleWithAllPermissions(Long id);
    void assignPermissionToRole(Long roleId, Long permissionId, Boolean assigned);
}
