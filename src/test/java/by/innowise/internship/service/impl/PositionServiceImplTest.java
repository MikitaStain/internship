package by.innowise.internship.service.impl;

import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.entity.Position;
import by.innowise.internship.exceptions.ResourceNotFoundException;
import by.innowise.internship.mappers.PositionMapper;
import by.innowise.internship.repository.dao.PositionRepository;
import by.innowise.internship.service.PositionService;
import by.innowise.internship.util.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * test for{@link PositionServiceImpl}
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PositionServiceImpl.class)
class PositionServiceImplTest {

    @MockBean
    private PositionRepository positionRepository;
    @MockBean
    private PositionMapper positionMapper;
    @MockBean
    private PagesService pagesService;
    @MockBean
    private Validation validation;

    @Autowired
    private PositionService positionService;

    private static final Long POSITION_ID = 1L;
    private static final String POSITION_NAME = "Java";
    private static final List<String> POSITIONS = Arrays.asList("Java", "C++");

    private final PositionDtoResponse expectedPositionDtoResponse = buildPositionDtoResponse();
    private final Position expectedPosition = buildPosition();
    private final PositionDTO expectedPositionDto = buildPositionDto();

    /**
     * {@link PositionServiceImpl#getPositionById(Long)}
     */
    @Test
    void should_PositionDtoResponse_When_ThisIdInDataBase() {

        //Before
        Optional<Position> positionById = Optional.of(buildPosition());

        //Mock
        Mockito.when(positionRepository.findById(POSITION_ID)).thenReturn(positionById);
        Mockito.when(positionMapper.toPositionResponseDto(expectedPosition)).thenReturn(expectedPositionDtoResponse);

        //Test
        PositionDtoResponse positionDtoResponse = positionService.getPositionById(POSITION_ID);

        //Then
        assertAll(() -> {
            assertNotNull(positionDtoResponse);
            assertEquals(expectedPositionDtoResponse, positionDtoResponse);
        });

    }

    /**
     * {@link PositionServiceImpl#getPositionById(Long)}
     */
    @Test
    void throws_ResourceNotFoundException_When_ThisIdNotInDataBase() {

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class
                , () -> positionService.getPositionById(POSITION_ID)
                , "Expected getPositionById() to throw, but it didn't");

        assertTrue(ex.getMessage().contains("position by id " + POSITION_ID + " not found"));

    }

//    /**
//     * {@link PositionServiceImpl#getPositionById(Long)}
//     */
//    @Test
//    void should_Long_When_PositionSaveInDataBase() {
//
//        Mockito.when(positionRepository
//                .findAll()
//                .stream()
//                .map(Position::getName)
//                .collect(Collectors.toList())).thenReturn(POSITIONS);
//
//        Mockito.verify(validation).checkDuplicateParameter(POSITIONS, POSITION_NAME);
//        Mockito.verify(validation).checkParameter(POSITION_NAME);
//
//        Mockito.when(Optional.of(expectedPositionDto)
//                .map(positionMapper::toPositionEntity)
//                .map(positionRepository::save)
//                .map(Position::getId)).thenReturn(Optional.of(POSITION_ID));
//
//        Long positionId = positionService.savePosition(expectedPositionDto);
//
//        assertAll(()->{
//            assertNotNull(positionId);
//            assertEquals(expectedPosition.getId(),positionId);
//        });
//
//
//
//
//    }

    private Position buildPosition() {
        Position position = new Position();
        position.setId(POSITION_ID);
        return position;
    }


    private PositionDtoResponse buildPositionDtoResponse() {

        return PositionDtoResponse
                .builder()
                .id(POSITION_ID)
                .name(POSITION_NAME)
                .build();
    }

    private PositionDTO buildPositionDto() {

        return PositionDTO
                .builder()
                .name(POSITION_NAME)
                .build();
    }


}