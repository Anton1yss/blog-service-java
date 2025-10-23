package by.AntonDemchuk.blog.mapper.reaction;

import by.AntonDemchuk.blog.database.entity.Reaction;
import by.AntonDemchuk.blog.dto.reaction.ReactionReadDto;
import by.AntonDemchuk.blog.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ReactionReadMapper implements BaseMapper<Reaction, ReactionReadDto> {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "postId", source = "post.id")
    public abstract ReactionReadDto toDto(Reaction reaction);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    public abstract Reaction toEntity(ReactionReadDto reactionReadDto);
}
