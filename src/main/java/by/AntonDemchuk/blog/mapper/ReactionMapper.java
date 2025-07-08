package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.Reaction;
import by.AntonDemchuk.blog.database.entity.ReactionType;
import by.AntonDemchuk.blog.dto.ReactionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ReactionMapper implements BaseMapper<Reaction, ReactionDto> {

    public abstract ReactionDto toDto(Reaction reaction);

    public abstract Reaction toEntity(ReactionDto reactionDto);

    public Reaction update(ReactionDto fromDto, Reaction reaction) {
        copy(fromDto, reaction);
        return reaction;
    }

    private void copy(ReactionDto reactionDto, Reaction reaction) {
        reaction.setReactionType(reactionDto.getReactionType());
    }
}
