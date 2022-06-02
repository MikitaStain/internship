package by.innowise.internship.security;

import by.innowise.internship.service.ServiceUserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    private final ServiceUserAuth service;

    @Autowired
    public UserSecurityService(ServiceUserAuth service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return UserSecurityFactory.addUserSecurity(service.getUserByLogin(username));
    }
}
