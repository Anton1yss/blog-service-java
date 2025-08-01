package by.AntonDemchuk.blog.database.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "reactions")
@Builder
@Data
@ToString(exclude = {"post", "user"})
@EqualsAndHashCode(exclude = {"post", "user"})
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {

    @EmbeddedId
    private ReactionId id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ReactionType reactionType;

}
