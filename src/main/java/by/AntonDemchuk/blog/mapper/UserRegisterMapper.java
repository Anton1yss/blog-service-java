package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.UserRegisterDto;
import jakarta.validation.constraints.Max;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserRegisterMapper implements BaseMapper<User, UserRegisterDto > {
}
