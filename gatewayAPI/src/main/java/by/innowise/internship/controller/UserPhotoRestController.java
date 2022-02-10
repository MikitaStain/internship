package by.innowise.internship.controller;

import by.innowise.internship.dto.responseDto.ImageDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gateway/users/{id_user}/photo")
@Api("gateway users photo")
public class UserPhotoRestController {

    private static final String USER_PHOTO_URL;
    private static final String PHOTO_URL;

    static {
        USER_PHOTO_URL = "http://localhost:8082/api/users/";
        PHOTO_URL = "/photo/";
    }

    private final RestTemplate restTemplate;

    @Autowired
    public UserPhotoRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("gateway method get for user photo")
    public ResponseEntity<String> addPhotoForUser(@PathVariable("id_user") Long userId,
                                                  @RequestParam MultipartFile multipartFile) {

        String forObject = restTemplate
                .postForObject(USER_PHOTO_URL + userId + PHOTO_URL, multipartFile, String.class);

        return new ResponseEntity<>(forObject, HttpStatus.OK);
    }

    @GetMapping("/{id_photo}")
    @ApiOperation("gateway method find image by id")
    public ResponseEntity<ImageDtoResponse> findPhotoForUser(@PathVariable ("id_user") Long userId,
                                                             @PathVariable("id_photo") String photoId){

        ImageDtoResponse forObject = restTemplate
                .getForObject(USER_PHOTO_URL + userId + PHOTO_URL + photoId, ImageDtoResponse.class);

        return new ResponseEntity<>(forObject, HttpStatus.OK);
    }

    @DeleteMapping("/{id_photo}")
    @ApiOperation("gateway method delete image from user")
    public ResponseEntity<HttpStatus> deleteImageForUser(@PathVariable ("id_user") Long userId,
                                                         @PathVariable("id_photo") String photoId){

        restTemplate.delete(USER_PHOTO_URL + userId + PHOTO_URL + photoId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
