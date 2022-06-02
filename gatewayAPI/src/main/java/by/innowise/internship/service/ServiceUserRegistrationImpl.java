package by.innowise.internship.service;

import by.innowise.internship.dto.requestDto.UserCreateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServiceUserRegistrationImpl implements ServiceUserRegistration{

    private final PasswordEncoder encoder;

    @Autowired
    public ServiceUserRegistrationImpl(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    @Override
    public UserCreateRequestDto encodingUserPassword(UserCreateRequestDto user) {

        user.setPassword(encoder.encode(user.getPassword()));

        return user;
    }
}
