package by.innowise.internship.service.impl;

import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PagesService<T> {

    public Pageable getPage(int size, int number, String sort) {

        return PageRequest.of(number,
                size,
                Sort.by(sort)
        );
    }

    public PagesDtoResponse getPagesDtoResponse(int size, int number, String sort, List all) {

        return PagesDtoResponse
                .builder()
                .page(number)
                .size(size)
                .sort(sort)
                .dto(all)
                .build();
    }
}
