package by.AntonDemchuk.blog.mapper.post;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.dto.post.PostDto;
import by.AntonDemchuk.blog.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class PostMapper implements BaseMapper<Post, PostDto> {

    public abstract PostDto toDto(Post post);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "reactions", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    public abstract Post toEntity(PostDto postDto);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "reactions", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(ignore = true , target = "creationDate")
    public abstract void updateFromDtoToEntity(PostDto fromDto, @MappingTarget() Post toEntity);

    public Post update(PostDto fromDto, Post toEntity) {
        updateFromDtoToEntity(fromDto, toEntity);
        return toEntity;
    }
}
