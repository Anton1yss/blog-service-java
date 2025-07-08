package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.UserReadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserReadMapper implements BaseMapper<User, UserReadDto> {
}
