package by.AntonDemchuk.blog.dto.reaction;

import by.AntonDemchuk.blog.database.entity.ReactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReactionDto {
    private ReactionType reactionType;
}
