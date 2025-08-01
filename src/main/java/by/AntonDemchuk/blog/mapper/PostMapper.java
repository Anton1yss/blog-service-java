package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.dto.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class PostMapper implements BaseMapper<Post, PostDto> {

    public abstract PostDto toDto(Post post);

    public abstract Post toEntity(PostDto postDto);

    @Mapping(ignore = true , target = "creationDate")
    public abstract void updateFromDtoToEntity(PostDto fromDto, @MappingTarget() Post toEntity);

    public Post update(PostDto fromDto, Post toEntity) {
        updateFromDtoToEntity(fromDto, toEntity);
        return toEntity;
    }
}
