package by.AntonDemchuk.blog.unit.service;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.user.UserDetailedReadDto;
import by.AntonDemchuk.blog.dto.user.UserDto;
import by.AntonDemchuk.blog.mapper.user.UserDetailedReadMapper;
import by.AntonDemchuk.blog.mapper.user.UserMapper;
import by.AntonDemchuk.blog.repository.UserRepository;
import by.AntonDemchuk.blog.service.SharedService;
import by.AntonDemchuk.blog.service.UserService;
import by.AntonDemchuk.blog.unit.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends BaseServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailedReadMapper userDetailedReadMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SharedService sharedService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void delete_shouldDeleteUser() {

        /* Arranging */
        when(sharedService.getCurrentUser()).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(userId);

        /* Acting */
        userService.delete();

        /* Verifying */
        verify(userRepository).deleteById(userId);
    }

    @Test
    void update_shouldUpdateUser() {

        /* Arranging */
        UserDto userToUpdateDto = UserDto.builder()
                .username("Username")
                .email("test@example.com")
                .build();

        when(sharedService.getCurrentUser()).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockedUser));
        when(userMapper.update(userToUpdateDto, mockedUser)).thenReturn(mockedUser);
        when(userRepository.save(mockedUser)).thenReturn(mockedUser);
        when(userMapper.toDto(mockedUser)).thenReturn(userDto);

        /* Acting */
        UserDto result = userService.update(userToUpdateDto);

        /* Asserting & Verifying */
        assertNotNull(result);
        assertEquals(userToUpdateDto, result);
        verify(userMapper).toDto(mockedUser);
        verify(userRepository).save(mockedUser);
        verify(userMapper).toDto(mockedUser);
    }

    @Test
    void findById_shouldReturnUserDetailedReadDto() {

        /* Arranging */
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockedUser));
        when(userDetailedReadMapper.toDto(mockedUser)).thenReturn(userDetailedReadDto);

        /* Acting */
        UserDetailedReadDto result = userService.findById(1L);

        /* Asserting & Verifying */
        assertEquals(result, userDetailedReadDto);
        assertEquals(result.getClass(), userDetailedReadDto.getClass());
        verify(userRepository).findById(1L);
        verify(userDetailedReadMapper).toDto(mockedUser);
    }

    @Test
    void findAll_shouldReturnPageDto() {

        /* Arranging */
        Page<User> mockedPage = mock(Page.class);

        PageDto<UserDetailedReadDto> expectedPageDto = PageDto.<UserDetailedReadDto>builder()
                .content(List.of(userDetailedReadDto))
                .totalPages(1)
                .totalElements(2)
                .pageNumber(0)
                .pageSize(5)
                .build();

        when(userRepository.findAll(pageable)).thenReturn(mockedPage);
        when(userDetailedReadMapper.toPageDto(mockedPage)).thenReturn(expectedPageDto);

        /* Acting */
        PageDto<UserDetailedReadDto> result = userService.findAll(pageable);

        /* Asserting & Verifying */
        assertNotNull(result);
        assertEquals(expectedPageDto.getContent(), result.getContent());
        assertEquals(expectedPageDto.getPageSize(), result.getPageSize());
        assertEquals(expectedPageDto.getPageNumber(), result.getPageNumber());
        assertEquals(expectedPageDto.getTotalElements(), result.getTotalElements());
        assertEquals(expectedPageDto.getTotalPages(), result.getTotalPages());
        assertTrue(result.getContent().stream().allMatch(i -> i instanceof UserDetailedReadDto));
        verify(userRepository).findAll(pageable);
        verify(userDetailedReadMapper).toPageDto(mockedPage);
    }
}