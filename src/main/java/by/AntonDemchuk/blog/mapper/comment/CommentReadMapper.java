package by.AntonDemchuk.blog.mapper.comment;

import by.AntonDemchuk.blog.database.entity.Comment;
import by.AntonDemchuk.blog.dto.comment.CommentReadDto;
import by.AntonDemchuk.blog.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentReadMapper implements BaseMapper<Comment, CommentReadDto> {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "author.id")
    public abstract CommentReadDto toDto(Comment comment);

    @Mapping(source = "postId", target = "post.id")
    @Mapping(source = "author.id", target = "user.id")
    public abstract Comment toEntity(CommentReadDto commentReadDto);

}
