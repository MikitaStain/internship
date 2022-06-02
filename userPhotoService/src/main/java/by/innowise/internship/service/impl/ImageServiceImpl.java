package by.innowise.internship.service.impl;

import by.innowise.internship.dto.ImageDtoResponse;
import by.innowise.internship.exceptions.EmptyFileException;
import by.innowise.internship.exceptions.FileNotAvailableException;
import by.innowise.internship.exceptions.NotAvailableContentException;
import by.innowise.internship.service.ImageService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final GridFsTemplate gridFsTemplate;

    @Autowired
    public ImageServiceImpl(GridFsTemplate gridFsTemplate) {

        this.gridFsTemplate = gridFsTemplate;
    }


    @Override
    public String saveImage(MultipartFile multipartFile, Long userId) {

        if (multipartFile.isEmpty()) {

            throw new EmptyFileException("File is empty");
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {

            DBObject metadata = new BasicDBObject();
            metadata.put("userId", userId);

            return gridFsTemplate.store(inputStream, multipartFile.getOriginalFilename(), metadata).toHexString();

        } catch (IOException e) {

            throw new FileNotAvailableException("File " + multipartFile.getOriginalFilename() + "don`t available");
        }
    }

    @Override
    public void deleteImage(String id, Long userId) {

        GridFSFile gridFsFile = getGridFsFile(id);
        checkUserIdFromData(userId, gridFsFile);

        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
    }


    @Override
    public ImageDtoResponse findImageById(String idImage, Long userId) {

        GridFSFile gridFsFile = getGridFsFile(idImage);
        checkUserIdFromData(userId, gridFsFile);
        GridFsResource resource = getResource(gridFsFile);

        try (InputStream inputStream = resource.getInputStream()) {

            return getImageDtoResponse(inputStream, String.valueOf(resource.getId()), resource.getFilename());

        } catch (IOException e) {

            throw new FileNotAvailableException("File available");
        }
    }


    private GridFSFile getGridFsFile(String id) {

        return Optional.ofNullable(gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))))
                .orElseThrow(
                        () -> new FileNotAvailableException("File by id: " + id + " not found"));
    }


    private ImageDtoResponse getImageDtoResponse(InputStream inputStream, String id, String name) {

        try {
            return ImageDtoResponse
                    .builder()
                    .id(id)
                    .name(name)
                    .content(inputStream.readAllBytes())
                    .build();
        } catch (IOException e) {
            throw new EmptyFileException("");
        }
    }


    private GridFsResource getResource(GridFSFile file) {

        return gridFsTemplate.getResource(file);
    }


    private void checkUserIdFromData(Long userId, GridFSFile gridFSFile) {

        if (!Objects.requireNonNull(gridFSFile.getMetadata()).containsValue(userId)) {

            throw new NotAvailableContentException("Content not available for user with id " + userId);
        }
    }

    @Override
    public void deleteAllImagesForUser(Long userId) {

        gridFsTemplate.delete(new Query(Criteria.where("metadata.userId").is(userId)));
    }

    @Override
    public List<ImageDtoResponse> findAllImagesForUser(Long userId) {

        List<ImageDtoResponse> collect =
                gridFsTemplate.find(new Query(Criteria.where("metadata.userId").is(userId))).into(new ArrayList<>())
                        .stream().map(this::gridFileToDto)
                        .collect(Collectors.toList());

        if (collect.isEmpty()) {

            throw new NotAvailableContentException("For user with id " + userId + " photo dont exist");
        }

        return collect;
    }

    private ImageDtoResponse gridFileToDto(GridFSFile gridFSFile) {

        try {
            return ImageDtoResponse.builder()
                    .content(Objects.requireNonNull(getResource(gridFSFile).getContent().readAllBytes()))
                    .name(Objects.requireNonNull(gridFSFile.getFilename()))
                    .id(Objects.requireNonNull(gridFSFile.getObjectId().toHexString()))
                    .build();

        } catch (IOException e) {

            throw new FileNotAvailableException("File not available " + gridFSFile.getFilename());
        }
    }

    @KafkaListener(topics = "delete", groupId = "delete1")
    private void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
        deleteAllImagesForUser(Long.parseLong(message));
    }
}
