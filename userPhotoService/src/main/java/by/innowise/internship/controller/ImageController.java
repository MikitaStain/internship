package by.innowise.internship.controller;

import by.innowise.internship.dto.ImageDtoResponse;
import by.innowise.internship.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("users")
@Api(value = "Image controller")
public class ImageController {

    private final ImageService service;

    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping(value = "/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("save photo")
    public ResponseEntity<ImageDtoResponse> saveImage(@RequestParam("file") MultipartFile multipartFile) {

        ImageDtoResponse image = service.saveImage(multipartFile);


        return new ResponseEntity<>(image, HttpStatus.OK);

    }
}
