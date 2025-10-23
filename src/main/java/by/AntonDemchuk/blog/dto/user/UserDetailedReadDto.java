package by.AntonDemchuk.blog.dto.user;

import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UserDetailedReadDto {
    private final Long id;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final LocalDate birthDate;
    private final String email;
    private final String personalInfo;
}
