package by.innowise.internship.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "firstCollections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Image {

    @Id
    @Field(name = "id")
    private String id;

    @Field(name = "content")
    private byte[] content;

    @Field(name = "name")
    private String name;
}
