package by.AntonDemchuk.blog.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@Builder
@ToString(exclude = {"user", "post"})
@EqualsAndHashCode(exclude = {"user", "post"})
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "post_date")
    private LocalDateTime postDate;

    @Column(name = "message")
    private String comment;
}
