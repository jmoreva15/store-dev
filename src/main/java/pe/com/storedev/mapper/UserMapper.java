package pe.com.storedev.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.storedev.dto.user.UserCreateDTO;
import pe.com.storedev.dto.user.UserDTO;
import pe.com.storedev.dto.user.UserUpdateDTO;
import pe.com.storedev.entity.User;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;

    public User toEntity(UserCreateDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setActive(dto.getActive());
        user.setRole(roleMapper.toEntity(dto.getRoleId()));

        return user;
    }

    public UserDTO toDTO(User entity) {
        if (entity == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setActive(entity.getActive());
        dto.setEmailVerified(entity.getEmailVerified());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setRole(roleMapper.toDTO(entity.getRole()));

        return dto;
    }

    public void updateEntity(User entity, UserUpdateDTO dto) {
        if (entity == null || dto == null) return;

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setActive(dto.getActive());
        entity.setRole(roleMapper.toEntity(dto.getRoleId()));
    }
}
