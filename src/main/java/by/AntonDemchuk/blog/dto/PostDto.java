package by.AntonDemchuk.blog.dto;

import by.AntonDemchuk.blog.database.entity.PostCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Value
public class PostDto {

    @NotNull(message = "Title cannot be empty.")
    @Size(max = 128, message = "Title's too long!")
    String title;

    @NotNull(message = "Description cannot be empty.")
    @Size(max = 512, message = "Description's too long!")
    String description;

    @NotNull
    LocalDateTime updateDate;

    @NotNull
    PostCategory category;
}
