package by.innowise.internship.service.impl;

import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.entity.Position;
import by.innowise.internship.exceptions.NoCreateException;
import by.innowise.internship.exceptions.NoDataFoundException;
import by.innowise.internship.exceptions.ResourceNotFoundException;
import by.innowise.internship.mappers.PositionMapper;
import by.innowise.internship.repository.dao.PositionRepository;
import by.innowise.internship.service.PositionService;
import by.innowise.internship.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final PagesService pagesService;
    private final Validation validation;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository
            , PositionMapper positionMapper
            , PagesService pagesService
            , Validation validation) {

        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
        this.pagesService = pagesService;
        this.validation = validation;
    }

    @Override
    @Transactional(readOnly = true)
    public PositionDtoResponse getPositionById(Long id) {

        return positionMapper.toPositionResponseDto(getPosition(id));
    }


    @Override
    public Long savePosition(PositionDTO positionDTO) {

        validation.checkDuplicateParameter(positionsName(), positionDTO.getName());
        validation.checkParameter(positionDTO.getName());

        return Optional.of(positionDTO)
                .map(positionMapper::toPositionEntity)
                .map(positionRepository::save)
                .map(Position::getId)
                .orElseThrow(() -> new NoCreateException("Failed to save position"));
    }

    private List<String> positionsName() {

        return positionRepository.findAll().stream()
                .map(Position::getName)
                .collect(Collectors.toList());
    }


    @Override
    public PositionDtoResponse updatePosition(PositionDTO positionDTO, Long id) {

        Position positionById = getPosition(id);
        Position position = positionMapper.toPositionEntity(positionDTO);

        if (!positionDTO.getName().isBlank()) {
            positionById.setName(position.getName());
        }

        positionRepository.save(positionById);

        return positionMapper
                .toPositionResponseDto(positionById);
    }

    @Override
    public void deletePosition(Long id) {

        Position position = getPosition(id);

        positionRepository.delete(position);
    }

    @Override
    public Position getPosition(Long id) {

        return positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("position by id " + id + " not found"));
    }


    @Override
    public PagesDtoResponse<PositionDtoResponse> getAll(int size, int page, String sort) {

        Page<PositionDtoResponse> allPositions =
                positionRepository.findAll(
                        pagesService.getPage(size, page, sort))
                .map(positionMapper::toPositionResponseDto);

        if (allPositions.isEmpty()) {

            throw new NoDataFoundException("Position not found");
        }
        return pagesService.getPagesDtoResponse(size, page, sort, allPositions.getContent());
    }
}
