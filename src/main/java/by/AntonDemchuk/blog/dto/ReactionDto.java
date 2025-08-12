package by.AntonDemchuk.blog.dto;

import by.AntonDemchuk.blog.database.entity.ReactionType;
import lombok.Data;

@Data
public class ReactionDto {
    private ReactionType reactionType;
}
