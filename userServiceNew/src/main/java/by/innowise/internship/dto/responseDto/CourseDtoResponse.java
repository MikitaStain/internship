package by.innowise.internship.dto.responseDto;

import by.innowise.internship.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDtoResponse {

    private Long id;

    private String name;

    private List<UserDto> users;

}
