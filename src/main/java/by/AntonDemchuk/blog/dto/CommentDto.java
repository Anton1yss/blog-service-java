package by.AntonDemchuk.blog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    @Size(min = 1, max = 512)
    @NotNull(message = "Comment cannot be Empty.")
    private String comment;

    @NotNull(message = "Creation Time cannot be Empty.")
    private LocalDateTime postDate;

    @NotNull
    private Long userId;

    @NotNull
    private Long postId;
}
