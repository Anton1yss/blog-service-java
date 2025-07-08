package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.UserDto;
import by.AntonDemchuk.blog.dto.UserReadDto;
import by.AntonDemchuk.blog.mapper.UserMapper;
import by.AntonDemchuk.blog.mapper.UserReadMapper;
import by.AntonDemchuk.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserReadMapper userReadMapper;

    public UserDto create(@Valid UserDto userCreateDto) {
        UserDto createdUser = userMapper.toDto(userRepository.save(userMapper.toEntity(userCreateDto)));
        log.info("User {} created successfully", createdUser.getEmail());

        return createdUser;
    }

    public void delete(@NotNull Long userId) {
        Optional<User> userToDelete = userRepository.findById(userId);

        if (userToDelete.isPresent()) {
            userRepository.deleteById(userId);
            log.info("User with ID {} deleted successfully", userToDelete.get().getId());
        } else throw new EntityNotFoundException("User with ID " + userId + " not found.");
    }

    public UserDto update(@Valid UserDto userToUpdateDto, @NotNull Long userId) {
        return userRepository.findById(userId)
                .map(entity -> userMapper.update(userToUpdateDto, entity))
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));
    }

    @Transactional(readOnly = true)
    public UserReadDto findById(@NotNull Long userId) {
        return userRepository.findById(userId)
                .map(userReadMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));
    }

    @Transactional(readOnly = true)
    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::toDto)
                .collect(Collectors.toList());
    }
}
