package by.AntonDemchuk.blog.controller;

import by.AntonDemchuk.blog.database.entity.PostCategory;
import by.AntonDemchuk.blog.dto.PostDto;
import by.AntonDemchuk.blog.dto.PostReadDto;
import by.AntonDemchuk.blog.dto.ReactionReadDto;
import by.AntonDemchuk.blog.service.PostService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}/")
    public ResponseEntity<PostReadDto> getPost(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @GetMapping(value = "/", params = {"pageSize", "pageNumber"})
    public ResponseEntity<Page<PostReadDto>> getAllPosts(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber) {

        var result = postService.findAll(pageSize, pageNumber);

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/", params = {"pageSize", "pageNumber", "categoryName"})
    public ResponseEntity<Page<PostReadDto>> getAllPostsByCategory(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(required = false) PostCategory categoryName) {

        return ResponseEntity.ok(postService.findAllByCategory(pageSize, pageNumber, categoryName));
    }

    @PostMapping("/")
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto, @NotNull Long userId) {
        return ResponseEntity.ok(postService.create(postDto, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(@RequestBody PostDto postDto, @PathVariable @NotNull Long id) {
        return ResponseEntity.ok(postService.update(postDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> delete(@PathVariable @NotNull Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }
}
