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
@IdClass(ReactionIds.class)
public class Reaction {

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ReactionType reactionType;

}
