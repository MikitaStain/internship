package by.innowise.internship.service;

import by.innowise.internship.dto.ImageDtoResponse;
import by.innowise.internship.exceptions.EmptyFileException;
import by.innowise.internship.exceptions.FileNotAvailableException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class ImageService {

    private final GridFsTemplate gridFsTemplate;


    @Autowired
    public ImageService(GridFsTemplate gridFsTemplate) {

        this.gridFsTemplate = gridFsTemplate;
    }


    public ObjectId saveImage(MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {

            //пустой файл
            throw new EmptyFileException("File is empty");
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {

            DBObject metadata = new BasicDBObject();
            metadata.put("user", "loginUser");

            return gridFsTemplate.store(inputStream, multipartFile.getName(), metadata);


        } catch (IOException e) {
            throw new FileNotAvailableException("File " + multipartFile.getName() + " available");

        }
    }


    public ImageDtoResponse findImageById(String idImage) {

        GridFsResource resource = getResource(getGridFsFile(idImage));

        try (InputStream inputStream = resource.getInputStream()) {

            return getImageDtoResponse(inputStream, String.valueOf(resource.getId()), resource.getFilename());

        } catch (IOException e) {
            throw new FileNotAvailableException("File available");
        }

    }

    private GridFSFile getGridFsFile(String id) {

        return Optional.ofNullable(gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))))
                .orElseThrow(
                        () -> new FileNotAvailableException("File by id: "+id+" not found"));

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
}
