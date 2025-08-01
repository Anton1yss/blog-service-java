package by.AntonDemchuk.blog.repository;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.database.entity.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<Post> findByIdAndUserId(Long postId, Long userId);

    Page<Post> findAllByUserId(Pageable pageable, Long userId);

    Page<Post> findAllByCategory(Pageable pageable, PostCategory category);
}
