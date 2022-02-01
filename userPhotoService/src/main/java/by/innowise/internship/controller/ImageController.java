package by.innowise.internship.controller;

import by.innowise.internship.dto.ImageDtoResponse;
import by.innowise.internship.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("users/photo")
@Api(value = "Image controller")
public class ImageController {

    private final ImageService service;

    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("save photo")
    public ResponseEntity<ObjectId> saveImage(@RequestParam(value = "file") MultipartFile multipartFile) {

        ObjectId objectId = service.saveImage(multipartFile);


        return new ResponseEntity<>(objectId, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Find image by id")
    public ResponseEntity<ImageDtoResponse> findImage(@PathVariable("id") String id){

        final ImageDtoResponse imageById = service.findImageById(id);

        return new ResponseEntity<>(imageById,HttpStatus.OK);
    }

}
