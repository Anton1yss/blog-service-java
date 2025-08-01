package by.AntonDemchuk.blog.repository;

import by.AntonDemchuk.blog.database.entity.Comment;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPostId(Pageable pageable,Long postId);

//    Comment findByPostIdAndUserId(@NotNull Long commentId, @NotNull Long postId, @NotNull Long userId);
}
