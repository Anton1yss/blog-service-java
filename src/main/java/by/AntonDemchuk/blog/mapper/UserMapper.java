package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.UserDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring") // componentModel = "spring" означає що має керуватися IoC
public abstract class UserMapper implements BaseMapper<User, UserDto> {

    public abstract UserDto toDto(User user);

    public abstract User toEntity(UserDto userDto);

    public User update(UserDto fromDto, User toEntity) {
        copy(fromDto, toEntity);
        return toEntity;
    }

    public void copy(UserDto userDto, User user) {
        user.setUsername(userDto.getUsername());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setBirthDate(userDto.getBirthDate());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPersonalInfo(userDto.getPersonalInfo());
    }

}
