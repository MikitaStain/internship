package by.innowise.internship.service;

import by.innowise.internship.dto.ImageDtoResponse;
import by.innowise.internship.mappers.ImageMapper;
import by.innowise.internship.model.Image;
import by.innowise.internship.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository repository;
    private final ImageMapper imageMapper;

    @Autowired
    public ImageService(ImageRepository repository, ImageMapper imageMapper) {
        this.repository = repository;
        this.imageMapper = imageMapper;
    }


    public ImageDtoResponse saveImage(MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {

            //пустой файл
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Image image = new Image();
        try {
            image.setContent(multipartFile.getBytes());
            image.setName(multipartFile.getOriginalFilename());
            return imageMapper.toDto(repository.save(image));
        } catch (IOException e) {

            //нет доступа к файлу
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
