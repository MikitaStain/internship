package by.innowise.internship.controller;

import by.innowise.internship.dto.PositionDTO;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.service.PositionService;
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

@RestController
@RequestMapping("/api/positions")
@Api(value = "Controller for position")
public class PositionRestController {

    private final PositionService positionService;

    @Autowired
    public PositionRestController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/{id}")
    @ApiOperation("getting a position by id")
    public ResponseEntity<PositionDtoResponse> getPosition(@PathVariable("id") Long id) {

        PositionDtoResponse positionById = positionService.getPositionById(id);

        return new ResponseEntity<>(positionById, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("save a position")
    public ResponseEntity<Long> createPosition(@RequestBody PositionDTO positionDTO) {

        Long idPosition = positionService.savePosition(positionDTO);

        return new ResponseEntity<>(idPosition, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("update a position by id")
    public ResponseEntity<PositionDtoResponse> updatePosition(@RequestBody PositionDTO positionDTO,
                                                              @PathVariable("id") Long id) {

        PositionDtoResponse position = positionService.updatePosition(positionDTO, id);

        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete position by id")
    public ResponseEntity<HttpStatus> deletePosition(@PathVariable("id") Long id) {

        positionService.deletePosition(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @ApiOperation("get all position")
    public ResponseEntity<PagesDtoResponse<PositionDtoResponse>> getAllPosition
            (@RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false,
                     defaultValue = "name") String name) {

        PagesDtoResponse<PositionDtoResponse> all = positionService.getAll(size, page, name);

        return new ResponseEntity<>(all, HttpStatus.OK);

    }
}
