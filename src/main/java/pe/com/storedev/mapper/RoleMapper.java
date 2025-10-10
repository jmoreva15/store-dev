package pe.com.storedev.mapper;

import org.springframework.stereotype.Component;
import pe.com.storedev.dto.permission.AssignPermissionDTO;
import pe.com.storedev.dto.rol.RoleCreateDTO;
import pe.com.storedev.dto.rol.RoleDTO;
import pe.com.storedev.dto.rol.RolePermissionsDTO;
import pe.com.storedev.dto.rol.RoleUpdateDTO;
import pe.com.storedev.entity.Permission;
import pe.com.storedev.entity.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {
    public Role toEntity(RoleCreateDTO dto) {
        if (dto == null) return null;

        Role role = new Role();
        role.setName(dto.getName());
        role.setActive(dto.getActive());
        return role;
    }

    public RoleDTO toDTO(Role entity) {
        if (entity == null) return null;

        RoleDTO dto = new RoleDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setActive(entity.getActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public void updateEntity(Role entity, RoleUpdateDTO dto) {
        if (entity == null || dto == null) return;

        entity.setName(dto.getName());
        entity.setActive(dto.getActive());
    }

    public RolePermissionsDTO toRolePermissionsDTO(Role role, List<Permission> allPermissions) {
        if (role == null || allPermissions == null) return null;

        Set<Long> assignedIds = role.getPermissions()
                .stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());

        List<AssignPermissionDTO> permissionDTOs = allPermissions.stream()
                .map(permission -> new AssignPermissionDTO(
                        permission.getId(),
                        permission.getName(),
                        assignedIds.contains(permission.getId())
                ))
                .toList();

        RolePermissionsDTO dto = new RolePermissionsDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setActive(role.getActive());
        dto.setPermissions(permissionDTOs);

        return dto;
    }
}
