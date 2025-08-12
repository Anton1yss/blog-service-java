package by.AntonDemchuk.blog.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Builder
@Data
@ToString()
@EqualsAndHashCode()
@AllArgsConstructor
@NoArgsConstructor
public class ReactionId implements Serializable {

    @Column(name = "user_id", insertable=false, updatable=false)
    private Long userId;

    @Column(name = "post_id", insertable=false, updatable=false)
    private Long postId;
}
