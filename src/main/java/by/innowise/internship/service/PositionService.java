package by.innowise.internship.service;

import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;

public interface PositionService {

    PositionDtoResponse getPositionById(Long id);

    Long savePosition(PositionDTO positionDTO);

    PositionDtoResponse updatePosition(PositionDTO positionDTO, Long id);

    void deletePosition(Long id);

    PagesDtoResponse<PositionDtoResponse> getAll(int size, int page, String sort);

}
