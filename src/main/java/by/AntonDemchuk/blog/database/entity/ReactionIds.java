package by.AntonDemchuk.blog.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionIds implements Serializable {
    private Long user;
    private Long post;
}
