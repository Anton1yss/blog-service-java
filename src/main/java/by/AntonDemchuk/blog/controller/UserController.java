package by.AntonDemchuk.blog.controller;

import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.user.UserDetailedReadDto;
import by.AntonDemchuk.blog.dto.user.UserDto;
import by.AntonDemchuk.blog.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailedReadDto> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findById(userId));
    }

    @GetMapping("/")
    public ResponseEntity<PageDto<UserDetailedReadDto>> getAllUsers(
            @ParameterObject @PageableDefault(size = 5, page = 0, sort = {}) Pageable pageable) {

        PageDto<UserDetailedReadDto> response = userService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser() {
        userService.delete();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.update(userDto));
    }
}
