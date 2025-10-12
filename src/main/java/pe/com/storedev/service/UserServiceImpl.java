package pe.com.storedev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.storedev.dto.user.UserCreateDTO;
import pe.com.storedev.dto.user.UserDTO;
import pe.com.storedev.dto.user.UserUpdateDTO;
import pe.com.storedev.entity.User;
import pe.com.storedev.exception.NotFoundException;
import pe.com.storedev.mapper.UserMapper;
import pe.com.storedev.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAllNotDeleted(pageable)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional
    public UserDTO create(UserCreateDTO dto) {
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
        User existing = userRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));

        userMapper.updateEntity(existing, dto);
        User updated = userRepository.save(existing);
        return userMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));

        user.setDeleted(true);
        userRepository.save(user);
    }
}
