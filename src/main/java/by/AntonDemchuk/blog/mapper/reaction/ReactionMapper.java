package by.AntonDemchuk.blog.mapper.reaction;

import by.AntonDemchuk.blog.database.entity.Reaction;
import by.AntonDemchuk.blog.dto.reaction.ReactionDto;
import by.AntonDemchuk.blog.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ReactionMapper implements BaseMapper<Reaction, ReactionDto> {

    public abstract ReactionDto toDto(Reaction reaction);

    public abstract Reaction toEntity(ReactionDto reactionDto);

}
