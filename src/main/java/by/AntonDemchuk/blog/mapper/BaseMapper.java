package by.AntonDemchuk.blog.mapper;

import by.AntonDemchuk.blog.dto.PageDto;

import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public interface BaseMapper<F, T> {

    T toDto(F object);

    F toEntity(T object);

    default PageDto<T> toPageDto(Page<F> page) {
        return PageDto.<T>builder()
                .pageNumber(page.getNumber())
                .content(page.getContent().stream()
                        .map(this::toDto)
                        .collect(Collectors.toList()))
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
