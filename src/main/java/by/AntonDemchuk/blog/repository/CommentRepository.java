package by.AntonDemchuk.blog.repository;

import by.AntonDemchuk.blog.database.entity.Comment;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(@NotNull Long postId);

    List<Comment> findByPostIdAndUserId(Long postId, Long userId);

    Optional<Comment> findById(Long commentId);

}
