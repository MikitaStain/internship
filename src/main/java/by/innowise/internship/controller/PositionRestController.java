package by.innowise.internship.controller;

import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.service.PositionService;
import by.innowise.internship.service.impl.PagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@Api(value = "Controller for position")
public class PositionRestController {

    private final PositionService positionService;
    private final PagesService pagesService;

    @Autowired
    public PositionRestController(PositionService positionService, PagesService pagesService) {
        this.positionService = positionService;

        this.pagesService = pagesService;
    }

    @GetMapping("/{id}")
    @ApiOperation("getting a position by id")
    public ResponseEntity<PositionDtoResponse> getPosition(@PathVariable("id") Long id) {

        try {
            PositionDtoResponse positionById = positionService.getPositionById(id);

            return new ResponseEntity<>(positionById, HttpStatus.OK);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position by id not found");
        }
    }

    @PostMapping
    @ApiOperation("save a position")
    public ResponseEntity<HttpStatus> createPosition(@RequestBody PositionDTO positionDTO) {

        try {
            positionService.savePosition(positionDTO);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("update a position by id")
    public ResponseEntity<HttpStatus> updatePosition(@RequestBody PositionDTO positionDTO,
                                                     @PathVariable("id") Long id) {

        try {
            positionService.updatePosition(positionDTO, id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position by id not found");
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete position by id")
    public ResponseEntity<HttpStatus> deletePosition(@PathVariable("id") Long id) {

        try {

            positionService.deletePosition(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position by id not found");
        }
    }

    @GetMapping
    @ApiOperation("get all position")
    public ResponseEntity<PagesDtoResponse> getAllPosition(@RequestParam(defaultValue = "5") int size,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "name") String name) {

        PagesDto pagesDto = pagesService.getPagesDto(size, page, name);

        List<PositionDtoResponse> allPosition = positionService
                .getAll(pagesDto)
                .getContent();

        if (allPosition.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "position is empty");
        }

        PagesDtoResponse pagesDtoResponse = pagesService.getPagesDtoResponse(pagesDto, allPosition);

        return new ResponseEntity<>(pagesDtoResponse, HttpStatus.OK);

    }
}
