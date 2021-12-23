package by.innowise.internship.service.impl;

import by.innowise.internship.dto.PagesDto;
import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.UserDto;
import by.innowise.internship.dto.responseDto.PositionDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.entity.User;
import by.innowise.internship.mappers.UserMapper;
import by.innowise.internship.mappers.responseMapper.PositionResponseMapper;
import by.innowise.internship.mappers.responseMapper.UserResponseMapper;
import by.innowise.internship.repository.dao.UserRepository;
import by.innowise.internship.service.UserService;
import net.sf.ehcache.CacheManager;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserResponseMapper userResponseMapper;
    private final PositionResponseMapper positionResponseMapper;
    private final SessionFactory sessionFactory;


    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           UserResponseMapper userResponseMapper, PositionResponseMapper positionResponseMapper, SessionFactory sessionFactory) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userResponseMapper = userResponseMapper;
        this.positionResponseMapper = positionResponseMapper;
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDtoResponse getUserById(Long id) {

        return userRepository.findById(id)
                .map(userResponseMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("user by id not found"));
    }


    @Override
    public void saveUser(UserCreateRequestDto userDto) {

        Optional.ofNullable(userDto)
                .map(userMapper::create)
                .map(userRepository::save)
                .orElseThrow();

    }

    @Override
    public void updateUser(UserDto userDto, Long id) {

        User userById = getUser(id);
        User user = userMapper.toEntity(userDto);

        userById.setName(user.getName());

        userById.setLogin(user.getLogin());
        userById.setLastName(user.getLastName());

        userById.setPassword(user.getPassword());

        userRepository.save(userById);
    }

    @Override
    public void deleteUser(Long id) {

        User user = getUser(id);

        userRepository.delete(user);
    }

    @Override
    public Page<UserDtoResponse> getAll(PagesDto pagesDto) {
        return userRepository.findAll(PagesService.getPage(pagesDto))
                .map(userResponseMapper::toDTO);
    }

    public User getUser(Long id) throws IllegalArgumentException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("user by id not found"));

        return user;
    }

    public boolean isLoginUser(String login) {

        return userRepository.findByLogin(login).isPresent();
    }

    @Override
    public void addPosition(Long id, PositionDtoResponse positionDtoResponse) {

        User user = getUser(id);

        user.setPosition(positionResponseMapper.toEntity(positionDtoResponse));

        userRepository.save(user);
    }
}
