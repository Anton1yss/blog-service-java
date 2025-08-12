package by.AntonDemchuk.blog.repository;

import by.AntonDemchuk.blog.database.entity.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByPostIdAndUserId(Long postId, Long userId);

    Page<Reaction> findAllByPostId(Long postId, Pageable pageable);

    Long countByPostId(Long postId);


}
