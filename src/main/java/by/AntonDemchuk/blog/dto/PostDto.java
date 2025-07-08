package by.AntonDemchuk.blog.dto;

import by.AntonDemchuk.blog.database.entity.PostCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // видалити потім
public class PostDto {

    @NotNull(message = "Title cannot be empty.")
    @Size(max = 128, message = "Title's too long!")
    private String title;

    @NotNull(message = "Description cannot be empty.")
    @Size(max = 512, message = "Description's too long!")
    private String description;

    @NotNull
    private PostCategory postCategory;
}
