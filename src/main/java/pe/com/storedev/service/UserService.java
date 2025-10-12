package pe.com.storedev.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.storedev.dto.user.UserCreateDTO;
import pe.com.storedev.dto.user.UserDTO;
import pe.com.storedev.dto.user.UserUpdateDTO;

public interface UserService {
    Page<UserDTO> findAll(Pageable pageable);
    UserDTO create(UserCreateDTO supplierCreateDTO);
    UserDTO update(Long id, UserUpdateDTO supplierUpdateDTO);
    UserDTO findById(Long id);
    void delete(Long id);
}
