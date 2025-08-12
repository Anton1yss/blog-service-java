package by.AntonDemchuk.blog.controller;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.UserDetailedReadDto;
import by.AntonDemchuk.blog.dto.UserDto;
import by.AntonDemchuk.blog.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailedReadDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<Page<UserDetailedReadDto>> getAllUsers(
            @ParameterObject @PageableDefault(size = 5, page = 0, sort = {}) Pageable pageable) {

        Page<UserDetailedReadDto> response = userService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal User user) {
        userService.delete(user.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, @AuthenticationPrincipal User user ) {
        return ResponseEntity.ok(userService.update(userDto,user.getId()));
    }
}
