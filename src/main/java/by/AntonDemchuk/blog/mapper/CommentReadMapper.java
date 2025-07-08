package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.Comment;
import by.AntonDemchuk.blog.dto.CommentDto;
import by.AntonDemchuk.blog.dto.CommentReadDto;
import jakarta.persistence.ManyToOne;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CommentReadMapper implements BaseMapper<Comment, CommentReadDto> {

    public abstract CommentReadDto toDto(Comment comment);

    public abstract Comment toEntity(CommentReadDto commentReadDto);
}
