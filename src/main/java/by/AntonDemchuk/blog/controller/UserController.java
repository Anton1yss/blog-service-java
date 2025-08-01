package by.AntonDemchuk.blog.controller;

import by.AntonDemchuk.blog.dto.PostReadDto;
import by.AntonDemchuk.blog.dto.UserDto;
import by.AntonDemchuk.blog.dto.UserReadDto;
import by.AntonDemchuk.blog.service.PostService;
import by.AntonDemchuk.blog.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<UserReadDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<Page<UserReadDto>> getAllUsers(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber) {

        Page<UserReadDto> response = userService.findAll(pageSize, pageNumber);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser(@RequestParam @NotNull Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        return ResponseEntity.ok( userService.create(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, @PathVariable @NotNull Long id){
        return ResponseEntity.ok(userService.update(userDto,id));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<Page<PostReadDto>> getAllUserPosts(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber,
            @PathVariable @NotNull Long id){

        return ResponseEntity.ok(postService.findAllByUserId(pageSize, pageNumber,id));
    }

//    @GetMapping("/{userId}/posts/{postId}")
//    public ResponseEntity<PostDetailedReadDto> getUserPost(@PathVariable @NotNull Long postId , @PathVariable @NotNull Long userId){
//        return ResponseEntity.ok(postService.findByPostIdAndUserId(postId, userId));
//    }
}
