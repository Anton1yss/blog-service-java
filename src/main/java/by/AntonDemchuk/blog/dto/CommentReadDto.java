package by.AntonDemchuk.blog.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CommentReadDto {
    private final Long id;
    private final String comment;
    private final LocalDateTime postDate;
    private final UserReadDto author;
    private final PostReadDto post;
}
