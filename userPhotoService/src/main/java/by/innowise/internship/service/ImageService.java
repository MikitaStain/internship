package by.innowise.internship.service;

import by.innowise.internship.dto.ImageDtoResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String saveImage(MultipartFile multipartFile, Long userId);

    ImageDtoResponse findImageById(String idImage, Long userId);

    void deleteImage(String id, Long userId);
}
