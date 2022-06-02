package by.innowise.internship.controller;

import by.innowise.internship.dto.requestDto.PositionDTO;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/gateway/positions")
@Api("Gateway positions")
public class PositionRestController {

    private final RestTemplate restTemplate;

    @Autowired
    public PositionRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String URL_POSITIONS;

    static {

        URL_POSITIONS = "http://localhost:8080/api/positions/";
    }

    @GetMapping("/{id}")
    @ApiOperation("gateway get position by id")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<PositionDtoResponse> getPositionById(@PathVariable("id") Long positionId) {

        PositionDtoResponse position =
                restTemplate.getForObject(URL_POSITIONS + positionId, PositionDtoResponse.class);

        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("gateway get all positions")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<PagesDtoResponse<PositionDtoResponse>> getAllPositions
            (@RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false,
                     defaultValue = "name") String name) {

        PagesDtoResponse<PositionDtoResponse> positions =
                restTemplate.getForObject(
                        URL_POSITIONS + "?size=" + size
                                + "&page=" + page
                                + "&name=" + name,
                        PagesDtoResponse.class);

        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("gateway add position")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Long> addPosition(@RequestBody PositionDTO position) {

        Long positionId = restTemplate.postForObject(URL_POSITIONS, position, Long.class);

        return new ResponseEntity<>(positionId, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("gateway delete position")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deletePositionById(@PathVariable("id") Long positionId) {

        restTemplate.delete(URL_POSITIONS + positionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ApiOperation("gateway change position")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PositionDtoResponse> putPosition(@PathVariable("id") Long positionId,
                                                           @RequestBody PositionDTO position) {

        HttpEntity<PositionDTO> positionRequest = new HttpEntity<>(position);

        return restTemplate
                .exchange(URL_POSITIONS + positionId,
                        HttpMethod.PUT,
                        positionRequest,
                        PositionDtoResponse.class);
    }
}
