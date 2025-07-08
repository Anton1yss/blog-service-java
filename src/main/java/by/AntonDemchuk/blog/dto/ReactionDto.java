package by.AntonDemchuk.blog.dto;

import by.AntonDemchuk.blog.database.entity.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReactionDto {
    private ReactionType reactionType;
}
