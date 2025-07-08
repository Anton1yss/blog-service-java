package by.AntonDemchuk.blog.mapper;

import org.mapstruct.Mapper;

public interface BaseMapper<F, T> {

    T toDto(F object);

    F toEntity(T object);

}
