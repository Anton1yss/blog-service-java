package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.UserDto;
import by.AntonDemchuk.blog.dto.UserDetailedReadDto;
import by.AntonDemchuk.blog.mapper.UserDetailedReadMapper;
import by.AntonDemchuk.blog.mapper.UserMapper;
import by.AntonDemchuk.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserDetailedReadMapper userDetailedReadMapper;

    public void delete(@NotNull Long userId) {
        Optional<User> userToDelete = userRepository.findById(userId);

        if (userToDelete.isPresent()) {
            userRepository.deleteById(userId);
            log.info("User (ID: {}) deleted successfully.", userToDelete.get().getId());
        } else throw new EntityNotFoundException("User (ID: " + userId + ") not found.");
    }

    public UserDto update(@Valid UserDto userToUpdateDto, @NotNull Long userId) {
        return userRepository.findById(userId)
                .map(entity -> userMapper.update(userToUpdateDto, entity))
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User (ID: " + userId + ") not found."));
    }

    @Transactional(readOnly = true)
    public UserDetailedReadDto findById(@NotNull Long userId) {
        return userRepository.findById(userId)
                .map(userDetailedReadMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User (ID: " + userId + ") not found."));
    }

    @Transactional(readOnly = true)
    public Page<UserDetailedReadDto> findAll(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(userDetailedReadMapper::toDto);
    }
}
