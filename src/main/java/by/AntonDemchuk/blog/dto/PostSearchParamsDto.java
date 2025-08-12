package by.AntonDemchuk.blog.dto;

import by.AntonDemchuk.blog.database.entity.PostCategory;
import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class PostSearchParamsDto {
    private final String title;
    private final PostCategory category;
}
