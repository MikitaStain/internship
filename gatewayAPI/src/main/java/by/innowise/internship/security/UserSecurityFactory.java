package by.innowise.internship.security;

import by.innowise.internship.dto.responseDto.RoleDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoForAuthResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public final class UserSecurityFactory {

    private UserSecurityFactory() {
    }

    public static UserSecurity addUserSecurity(UserDtoForAuthResponse user) {

        return new UserSecurity(user.getId(), user.getLogin(), user.getPassword(), mapToAuthority(user.getRole()));

    }

    private static List<GrantedAuthority> mapToAuthority(RoleDtoResponse role) {

        return List.of(new SimpleGrantedAuthority(role.getName()));
    }
}
