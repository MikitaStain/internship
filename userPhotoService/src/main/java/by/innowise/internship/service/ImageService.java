package by.innowise.internship.service;

import by.innowise.internship.dto.ImageDtoResponse;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ObjectId saveImage(MultipartFile multipartFile);

    ImageDtoResponse findImageById(String idImage);
}
