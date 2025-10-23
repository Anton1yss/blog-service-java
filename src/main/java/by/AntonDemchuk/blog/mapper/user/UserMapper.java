package by.AntonDemchuk.blog.mapper.user;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.user.UserDto;
import by.AntonDemchuk.blog.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public abstract class UserMapper implements BaseMapper<User, UserDto> {

    public abstract UserDto toDto(User user);

    public abstract User toEntity(UserDto userDto);

    public abstract void updateFromDtoToEntity(UserDto fromDto, @MappingTarget User toEntity);

    public User update(UserDto fromDto, User toEntity) {
        updateFromDtoToEntity(fromDto, toEntity);
        return toEntity;
    }
}
