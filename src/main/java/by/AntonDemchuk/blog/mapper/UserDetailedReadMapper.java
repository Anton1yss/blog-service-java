package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.UserDetailedReadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserDetailedReadMapper implements BaseMapper<User, UserDetailedReadDto> {
}
