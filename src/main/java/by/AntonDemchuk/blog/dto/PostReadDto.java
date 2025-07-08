package by.AntonDemchuk.blog.dto;

import by.AntonDemchuk.blog.database.entity.PostCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PostReadDto {
    private final Long id;
    private final String title;
    private final String description;
    private final PostCategory category;
    private final LocalDateTime creationDate;
    private final UserReadDto author;
}
