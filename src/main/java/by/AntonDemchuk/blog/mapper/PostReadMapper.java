package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.dto.PostDto;
import by.AntonDemchuk.blog.dto.PostReadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PostReadMapper implements BaseMapper<PostReadDto, Post> {

    public abstract PostReadDto toDto(Post post);

    public abstract Post toEntity(PostReadDto postReadDto);

}
