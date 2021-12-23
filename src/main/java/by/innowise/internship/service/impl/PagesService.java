package by.innowise.internship.service.impl;

import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PagesService {

    public static Pageable getPage(PagesDto pagesDto) {

        return PageRequest.of(pagesDto.getPageNumber(),
                pagesDto.getPageSize(),
                Sort.by(pagesDto.getSort())
        );
    }

    public PagesDto getPagesDto(int size, int number, String sort) {

        return PagesDto
                .builder()
                .pageSize(size)
                .pageNumber(number)
                .sort(sort)
                .build();
    }

    public PagesDtoResponse getPagesDtoResponse(PagesDto pagesDto, List all) {

        return PagesDtoResponse
                .builder()
                .page(pagesDto.getPageNumber())
                .size(pagesDto.getPageSize())
                .sort(pagesDto.getSort())
                .dto(all)
                .build();
    }

}
