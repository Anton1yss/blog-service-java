package by.AntonDemchuk.blog.dto.reaction;

import by.AntonDemchuk.blog.database.entity.ReactionType;
import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ReactionReadDto {
    private final Long userId;
    private final Long postId;
    private final ReactionType reactionType;
}
