package by.innowise.internship.service.impl;

import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.UserDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.entity.User;
import by.innowise.internship.exceptions.NoCreateException;
import by.innowise.internship.exceptions.NoDataFoundException;
import by.innowise.internship.exceptions.ResourceNotFoundException;
import by.innowise.internship.mappers.UserMapper;
import by.innowise.internship.repository.dao.PositionRepository;
import by.innowise.internship.repository.dao.UserRepository;
import by.innowise.internship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PositionRepository positionRepository;
    private final PagesService pagesService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PositionRepository positionRepository,
                           PagesService pagesService) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.positionRepository = positionRepository;
        this.pagesService = pagesService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDtoResponse getUserById(Long id) {

        return userMapper.toUserResponseDto(getUser(id));
    }


    @Override
    public Long saveUser(UserCreateRequestDto userDto) {

        return Optional.ofNullable(userDto)
                .map(userMapper::create)
                .map(userRepository::save)
                .map(User::getId)
                .orElseThrow(() -> new NoCreateException("User can not created"));
    }

    @Override
    public UserDtoResponse updateUser(UserDto userDto, Long id) {

        User userById = getUser(id);
        User updateUser = userMapper.toEntity(userDto);

        User save = userRepository.save(update(userById, updateUser));

        return userMapper.toUserResponseDto(save);
    }

    private User update(User user, User updateUser) {

        if (!updateUser.getName().isBlank()) {
            user.setName(updateUser.getName());
        }

        if (!updateUser.getLastName().isBlank()) {
            user.setLastName(updateUser.getLastName());
        }

        if (!updateUser.getPassword().isBlank()) {
            user.setPassword(updateUser.getPassword());
        }

        return user;
    }

    @Override
    public void deleteUser(Long id) {

        User user = getUser(id);

        userRepository.delete(user);
    }

    @Override
    public PagesDtoResponse<UserDtoResponse> getAll(int size, int page, String sort) {

        Page<UserDtoResponse> allUsers = userRepository
                .findAll(pagesService.getPage(size, page, sort))
                .map(userMapper::toUserResponseDto);

        if (allUsers.isEmpty()) {

            throw new NoDataFoundException("Users not found");
        }
        return pagesService.getPagesDtoResponse(size, page, sort, allUsers.getContent());
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User getUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("user by id " + id + " not found"));
    }

    @Override
    public void addPosition(Long id, Long idPosition) {

        User user = getUser(id);

        user.setPosition(
                positionRepository.findById(idPosition)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("position by id " + idPosition + " not found"))
        );

        userRepository.save(user);
    }
}