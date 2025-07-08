package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.Comment;
import by.AntonDemchuk.blog.dto.CommentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CommentMapper implements BaseMapper<Comment, CommentDto> {

    public abstract CommentDto toDto(Comment comment);

    public abstract Comment toEntity(CommentDto commentDto);

    public Comment update(CommentDto commentDto, Comment comment) {
        copy(commentDto, comment);
        return comment;
    }

    private void copy(CommentDto fromDto, Comment comment) {
        comment.setComment(fromDto.getComment());
    }
}
