package by.innowise.internship.service;

import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import org.springframework.data.domain.Page;

public interface PositionService {

    PositionDtoResponse getPositionById(Long id);

    void savePosition(PositionDTO positionDTO);

    void updatePosition(PositionDTO positionDTO, Long id);

    void deletePosition(Long id);

    Page<PositionDtoResponse> getAll(PagesDto pagesDto);

    PositionDTO getDtoPosition(Long id);

}
