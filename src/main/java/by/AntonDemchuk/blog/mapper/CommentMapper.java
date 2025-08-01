package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.Comment;
import by.AntonDemchuk.blog.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class CommentMapper implements BaseMapper<Comment, CommentDto> {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    public abstract CommentDto toDto(Comment comment);

    @Mapping(source = "postId", target = "post.id")
    @Mapping(source = "userId", target = "user.id")
    public abstract Comment toEntity(CommentDto commentDto);

    public abstract void updateFromDtoToEntity(CommentDto fromDto, @MappingTarget Comment comment);

    public Comment update(CommentDto commentDto, Comment comment) {
        updateFromDtoToEntity(commentDto, comment);
        return comment;
    }
}
