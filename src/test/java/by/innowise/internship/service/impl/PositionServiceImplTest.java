package by.innowise.internship.service.impl;

import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.entity.Position;
import by.innowise.internship.exceptions.NoCreateException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
    private static final List<String> POSITIONS_NAME = new ArrayList<>();
    private static final List<Position> POSITIONS = new ArrayList<>();
    private static final int PAGE = 1;
    private static final int NUMBER = 1;
    private static final String SORTED = "name";

    private final PositionDtoResponse expectedPositionDtoResponse = buildPositionDtoResponse();
    private final Position expectedPosition = buildPosition();
    private final PositionDTO expectedPositionDto = buildPositionDto();
    private final Pageable expectedPageable = buildPageable();
    private final PagesDtoResponse<Object> expectedPagesDtoResponse = buildPagesDtoResponse();

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

    /**
     * {@link PositionServiceImpl#getPositionById(Long)}
     */
    @Test
    void should_Long_When_PositionSaveInDataBase() {

        Mockito.when(positionRepository
                .findAll()
                .stream()
                .map(Position::getName)
                .collect(Collectors.toList())).thenReturn(POSITIONS_NAME);

        verify(validation, never()).checkDuplicateParameter(POSITIONS_NAME, POSITION_NAME);
        verify(validation, never()).checkParameter(POSITION_NAME);

        Mockito.when(positionMapper.toPositionEntity(expectedPositionDto)).thenReturn(expectedPosition);
        Mockito.when(positionRepository.save(expectedPosition)).thenReturn(expectedPosition);

        assertAll(() -> {
            assertNotNull(expectedPositionDtoResponse.getId());
            assertEquals(POSITION_ID, expectedPositionDtoResponse.getId());
        });
    }

    @Test
    void throws_NoCreateException_When_PositionNotSaveInDataBase() {

        NoCreateException ex = assertThrows(NoCreateException.class
                , () -> positionService.savePosition(expectedPositionDto)
                , "Expected savePosition() to throw, but it didn't");

        assertTrue(ex.getMessage().contains("Failed to save position"));
    }

    @Test
    void should_PositionDtoResponse_When_PositionUpdateSuccessful() {

        //Before
        Optional<Position> positionById = Optional.of(buildPosition());

        //Mock
        Mockito.when(positionRepository.findById(POSITION_ID)).thenReturn(positionById);
        Mockito.when(positionMapper.toPositionEntity(expectedPositionDto)).thenReturn(expectedPosition);
        Mockito.when(positionRepository.save(expectedPosition)).thenReturn(expectedPosition);
        Mockito.when(positionMapper.toPositionResponseDto(expectedPosition)).thenReturn(expectedPositionDtoResponse);

        //Test
        PositionDtoResponse positionDtoResponse = positionService.updatePosition(expectedPositionDto, POSITION_ID);

        //Then
        assertAll(() -> {
            assertNotNull(positionDtoResponse);
            assertEquals(expectedPositionDtoResponse, positionDtoResponse);
        });
    }

    @Test
    void throws_ResourceNotFoundException_When_PositionByIdNotFound() {

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class
                , () -> positionService.updatePosition(expectedPositionDto, POSITION_ID)
                , "Expected updatePosition() to throw, but it didn't");

        assertTrue(ex.getMessage().contains("position by id " + POSITION_ID + " not found"));
    }

//    @Test
//    void throws_NoUpdateException_When_PositionNotUpdate() {
//
//        assertThrows(NoUpdateException.class
//                , () -> positionService.updatePosition(expectedPositionDto, POSITION_ID)
//                , "Expected updatePosition() to throw, but it didn't");
//
//    }


    @Test
    void should_PagesDtoResponse_When_InDataBaseThereAreSavedPositions() {


        //Mock
        Mockito.when(positionRepository.findAll()).thenReturn(POSITIONS);
        Mockito.when(pagesService.getPage(PAGE, NUMBER, SORTED)).thenReturn(expectedPageable);
        Mockito.when(positionMapper.toPositionResponseDto(expectedPosition)).thenReturn(expectedPositionDtoResponse);
        Mockito.when(pagesService.getPagesDtoResponse(PAGE, NUMBER, SORTED, POSITIONS_NAME)).thenReturn(expectedPagesDtoResponse);


    }


    private Position buildPosition() {
        Position position = new Position();
        position.setId(POSITION_ID);
        position.setName(POSITION_NAME);
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

    private Pageable buildPageable() {

        return PageRequest.
                of(NUMBER, PAGE, Sort.by(SORTED));
    }

    private PagesDtoResponse buildPagesDtoResponse() {

        return PagesDtoResponse
                .builder()
                .page(NUMBER)
                .size(PAGE)
                .sort(SORTED)
                .dto(new ArrayList<>())
                .build();
    }


}