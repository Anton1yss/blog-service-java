package by.AntonDemchuk.blog.dto.comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    @Size(min = 1, max = 512)
    @NotNull(message = "Comment cannot be Empty.")
    private String comment;

    @NotNull(message = "Creation Time cannot be Empty.")
    private LocalDateTime postDate;
}
