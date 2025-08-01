package by.AntonDemchuk.blog.controller;

import by.AntonDemchuk.blog.dto.CommentDto;
import by.AntonDemchuk.blog.dto.CommentReadDto;
import by.AntonDemchuk.blog.dto.PostDto;
import by.AntonDemchuk.blog.service.CommentService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<Page<CommentReadDto>> getAllCommentsByPostId(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber,
            @PathVariable @NotNull Long postId) {

        return ResponseEntity.ok(commentService.findAllByPostId(postId, pageSize, pageNumber));
    }

    @PostMapping("/")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @RequestParam Long postId,
            @RequestParam Long userId) {

        return ResponseEntity.ok(commentService.create(commentDto, postId, userId));
    }

    @DeleteMapping("/")
    public ResponseEntity<PostDto> deleteComment(@RequestParam @NotNull Long id){
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }

//    @PutMapping("/")
//    public ResponseEntity<CommentDto> updateComment(
//            @RequestBody CommentDto commentDto,
//            @RequestParam Long id) {
//
//        return ResponseEntity.ok(commentService.update(commentDto, id));
//    }
}
