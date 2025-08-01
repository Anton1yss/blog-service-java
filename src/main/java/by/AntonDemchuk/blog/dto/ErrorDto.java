package by.AntonDemchuk.blog.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ErrorDto {
    int code;
    String message;
    List<String> errors;
}
