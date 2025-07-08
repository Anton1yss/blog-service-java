package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.dto.PostDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PostMapper implements BaseMapper<Post, PostDto> {

    public abstract PostDto toDto(Post post);
    public abstract Post toEntity(PostDto postDto);

    public Post update(PostDto fromDto, Post toEntity) {
        copy(fromDto, toEntity);
        return toEntity;
    }

    public void copy(PostDto postDto, Post post) {
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setCategory(postDto.getPostCategory());
    }
}
