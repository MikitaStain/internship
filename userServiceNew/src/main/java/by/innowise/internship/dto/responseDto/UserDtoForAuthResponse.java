package by.innowise.internship.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoForAuthResponse {

    private Long id;

    private String login;

    private String password;

    private RoleDtoResponse role;
}
