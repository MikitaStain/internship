package by.innowise.internship.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoForFilter {

    private String name;

    private String lastName;

    private String login;

    private String positions;

    private String courses;

}
