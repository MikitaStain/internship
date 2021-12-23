package by.innowise.internship.dto.responseDto;

import by.innowise.internship.dto.CourseDto;
import by.innowise.internship.dto.EmailDto;
import by.innowise.internship.dto.PositionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoResponse {

    private Long id;

    private String login;

    private String name;

    private String lastName;

    private List<EmailDtoResponse> emails;

    private List<CourseDto> courses;

    private PositionDTO position;
}
