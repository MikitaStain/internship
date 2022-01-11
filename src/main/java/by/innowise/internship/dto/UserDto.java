package by.innowise.internship.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String name;

    private String lastName;

    private String password;

    private List<EmailDto> emails;

//    private List<CourseDto> courses;

//    private PositionDTO position;


}
