package by.AntonDemchuk.blog.dto;

import lombok.*;


@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UserReadDto {
    private final Long id;
    private final String username;
    private final String email;
}
