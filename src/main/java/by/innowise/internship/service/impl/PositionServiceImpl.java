package by.innowise.internship.service.impl;

import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.entity.Position;
import by.innowise.internship.mappers.PositionMapper;
import by.innowise.internship.mappers.responseMapper.PositionResponseMapper;
import by.innowise.internship.repository.dao.PositionRepository;
import by.innowise.internship.service.PositionService;
import by.innowise.internship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final PositionResponseMapper positionMapperResponse;
    private final PositionMapper positionMapper;
    private final UserService userService;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository
            , PositionResponseMapper positionMapperResponse
            , PositionMapper positionMapper, UserService userService) {

        this.positionRepository = positionRepository;
        this.positionMapperResponse = positionMapperResponse;
        this.positionMapper = positionMapper;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public PositionDtoResponse getPositionById(Long id) {

        return positionRepository.findById(id)
                .map(positionMapperResponse::toDTO)
                .orElseThrow();
    }


    @Override
    public void savePosition(PositionDTO positionDTO) {

        Optional.ofNullable(positionDTO)
                .map(positionMapper::toEntity)
                .map(positionRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("Failed to save position"));
    }

    @Override
    public void updatePosition(PositionDTO positionDTO, Long id) {

        Position positionById = getPosition(id);
        Position position = positionMapper.toEntity(positionDTO);

        positionById.setName(position.getName());

        positionRepository.save(positionById);
    }

    @Override
    public void deletePosition(Long id) {

        Position position = getPosition(id);

        positionRepository.delete(position);
    }

    private Position getPosition(Long id) throws IllegalArgumentException {

        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("position by id not found"));

        return position;

    }

    @Override
    public Page<PositionDtoResponse> getAll(PagesDto pagesDto) {

        return positionRepository.findAll(PagesService.getPage(pagesDto))
                .map(positionMapperResponse::toDTO);
    }

    @Override
    public PositionDTO getDtoPosition(Long id) {

        return positionRepository.findById(id)
                .map(positionMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Position by id not found"));

    }
}
