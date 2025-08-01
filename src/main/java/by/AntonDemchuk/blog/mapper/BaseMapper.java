package by.AntonDemchuk.blog.mapper;

public interface BaseMapper<F, T> {

    T toDto(F object);

    F toEntity(T object);

}
