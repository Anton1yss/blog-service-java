package by.AntonDemchuk.blog.dto;

import by.AntonDemchuk.blog.database.entity.PostCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
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
