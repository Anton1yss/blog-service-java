package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.user.UserDto;
import by.AntonDemchuk.blog.dto.user.UserDetailedReadDto;
import by.AntonDemchuk.blog.mapper.user.UserDetailedReadMapper;
import by.AntonDemchuk.blog.mapper.user.UserMapper;
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

    private final SharedService sharedService;

    public void delete() {
        User authrodizedUser = sharedService.getCurrentUser();
        userRepository.deleteById(authrodizedUser.getId());
        log.info("User (ID: {}) deleted successfully.", authrodizedUser.getId());
    }

    public UserDto update(@Valid UserDto userToUpdateDto) {

        User authrodizedUser = sharedService.getCurrentUser();

        return userRepository.findById(authrodizedUser.getId())
                .map(entity -> userMapper.update(userToUpdateDto, entity))
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User (ID: " + authrodizedUser.getId() + ") not found."));
    }

    @Transactional(readOnly = true)
    public UserDetailedReadDto findById(@NotNull Long userId) {
        return userRepository.findById(userId)
                .map(userDetailedReadMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User (ID: " + userId + ") not found."));
    }

    @Transactional(readOnly = true)
    public PageDto<UserDetailedReadDto> findAll(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);

        return userDetailedReadMapper.toPageDto(usersPage);
    }
}
