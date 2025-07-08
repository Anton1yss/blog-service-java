package by.AntonDemchuk.blog.dto;

import by.AntonDemchuk.blog.database.entity.ReactionType;
import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ReactionReadDto {
    private final Long userId;
    private final Long postId;
    private final ReactionType reactionType;
}
