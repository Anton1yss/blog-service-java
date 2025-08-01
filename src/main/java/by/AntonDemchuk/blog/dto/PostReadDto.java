package by.AntonDemchuk.blog.dto;

import by.AntonDemchuk.blog.database.entity.PostCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class PostReadDto {
    private final Long id;
    private final String title;
    private final String description;
    private final PostCategory category;
    private final LocalDateTime creationDate;
    private final UserReadDto author;
    private final int reactionCount;
    private final int commentCount;
}
