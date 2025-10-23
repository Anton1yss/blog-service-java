package by.AntonDemchuk.blog.repository;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.dto.post.PostSearchParamsDto;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {

    public static Specification<Post> buildSpecification(PostSearchParamsDto postSearchParamsDto) {
        Specification<Post> specification = Specification.where(null);

        if (postSearchParamsDto.getTitle() != null && !postSearchParamsDto.getTitle().trim().isEmpty()) {

            System.out.println(postSearchParamsDto.getTitle());

            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            root.get("title"),
                            "%" + postSearchParamsDto.getTitle() + "%"));
        }

        if (postSearchParamsDto.getCategory() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category"), postSearchParamsDto.getCategory()));
        }

        return specification;

    }
}
