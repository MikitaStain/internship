package by.innowise.internship.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto {

    private String name;

    private String lastName;

    private String password;

    private Long positionId;

    private Long courseId;
}
