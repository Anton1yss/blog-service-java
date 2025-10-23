package by.AntonDemchuk.blog.mapper.user;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.user.UserRegisterDto;
import by.AntonDemchuk.blog.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserRegisterMapper implements BaseMapper<User, UserRegisterDto > {
}
