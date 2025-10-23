package by.AntonDemchuk.blog.mapper.post;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.post.PostReadDto;
import by.AntonDemchuk.blog.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public abstract class PostReadMapper implements BaseMapper<Post, PostReadDto> {

    @Mapping(source = "user", target = "author")
    @Mapping(target = "reactionCount", expression = "java(post.getReactions().size())")
    @Mapping(target = "commentCount", expression = "java(post.getComments().size())")
    public abstract PostReadDto toDto(Post post);

    @Mapping(source = "author", target = "user")
    public abstract Post toEntity(PostReadDto postReadDto);

}
