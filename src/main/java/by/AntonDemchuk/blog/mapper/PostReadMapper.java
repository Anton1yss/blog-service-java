package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.dto.PostReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PostReadMapper {

    @Mapping(source = "user", target = "author")
    @Mapping(target = "reactionCount", expression = "java(post.getReactions().size())")
    @Mapping(target = "commentCount", expression = "java(post.getComments().size())")
    public abstract PostReadDto toDto(Post post);

    @Mapping(source = "author", target = "user")
//    @Mapping(target = "reactionCount", ignore = true)
//    @Mapping(target = "commentCount", ignore = true)
    public abstract Post toEntity(PostReadDto postReadDto);
}
