package by.innowise.internship.controller;

import by.innowise.internship.dto.ImageDtoResponse;
import by.innowise.internship.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users/{id_user}/photo")
@Api(value = "Image controller")
public class ImageController {

    private final ImageService service;

    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("save photo")
    public ResponseEntity<String> saveImage(@PathVariable("id_user") Long userId,
                                            @RequestParam(value = "file") MultipartFile multipartFile) {

        String objectId = service.saveImage(multipartFile, userId);

        return new ResponseEntity<>(objectId, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Find image by id")
    public ResponseEntity<ImageDtoResponse> findImage(@PathVariable("id_user") Long userId,
                                                      @PathVariable("id") String id) {

        ImageDtoResponse imageById = service.findImageById(id, userId);

        return new ResponseEntity<>(imageById, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete image by id")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable("id_user") Long userId,
                                                  @PathVariable("id") String id) {

        service.deleteImage(id, userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
