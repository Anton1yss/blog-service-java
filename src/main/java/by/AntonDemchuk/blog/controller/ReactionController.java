package by.AntonDemchuk.blog.controller;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.ReactionDto;
import by.AntonDemchuk.blog.dto.ReactionReadDto;
import by.AntonDemchuk.blog.service.ReactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reactions")
@RequiredArgsConstructor
@Tag(name = "Reaction Controller")
public class ReactionController {

    private final ReactionService reactionService;

    @GetMapping("/{postId}/count")
    public Long getReactionCountByPost(@PathVariable @NotNull Long postId) {
        return reactionService.countAllByPostId(postId);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Page<ReactionReadDto>> getReactionById(
            @PathVariable @NotNull Long postId,
            @ParameterObject @PageableDefault(size = 5, page = 0, sort = {}) Pageable pageable) {

        return ResponseEntity.ok(reactionService.findAllByPostId(postId, pageable));
    }

    @PostMapping("/")
    public ResponseEntity<ReactionDto> create(
            @Valid ReactionDto reactionDto,
            @AuthenticationPrincipal User user,
            @RequestParam @NotNull Long postId) {

        return ResponseEntity.ok(reactionService.create(reactionDto, postId, user.getId() ));
    }

    @DeleteMapping("/")
    public ResponseEntity<ReactionDto> delete(
            @RequestParam @NotNull Long postId,
            @AuthenticationPrincipal User user){

        reactionService.delete(postId, user.getId());
        return ResponseEntity.ok().build();
    }
}
