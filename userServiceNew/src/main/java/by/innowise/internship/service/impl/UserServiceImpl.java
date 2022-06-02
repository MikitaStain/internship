package by.innowise.internship.service.impl;

import by.innowise.internship.dto.UpdateUserDto;
import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoForAuthResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.entity.Course;
import by.innowise.internship.entity.Role;
import by.innowise.internship.entity.RoleEnum;
import by.innowise.internship.entity.User;
import by.innowise.internship.exceptions.NoCreateException;
import by.innowise.internship.exceptions.NoDataFoundException;
import by.innowise.internship.exceptions.ResourceNotFoundException;
import by.innowise.internship.mappers.UserMapper;
import by.innowise.internship.repository.dao.UserRepository;
import by.innowise.internship.repository.specifications.UserSpecifications;
import by.innowise.internship.service.CourseGlobalService;
import by.innowise.internship.service.PositionGlobalService;
import by.innowise.internship.service.UserService;
import by.innowise.internship.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PagesService pagesService;
    private final PositionGlobalService positionService;
    private final CourseGlobalService courseService;
    private final Validation validation;
    private final KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PagesService pagesService,
                           PositionGlobalService positionService,
                           CourseGlobalService courseService,
                           Validation validation,
                           KafkaTemplate<String, String> kafkaTemplate) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.pagesService = pagesService;
        this.positionService = positionService;
        this.courseService = courseService;
        this.validation = validation;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDtoResponse getUserById(Long id) {

        return userMapper.toUserResponseDto(getUser(id));
    }


    @Override
    public Long saveUser(UserCreateRequestDto userDto) {

        validation.checkDuplicateParameter(userLogins(), userDto.getLogin());
        validation.checkParameter(userDto.getLogin());

        return Optional.of(userDto)
                .map(userMapper::create)
                .map(this::setRole)
                .map(userRepository::save)
                .map(User::getId)
                .orElseThrow(() -> new NoCreateException("User can not created"));
    }

    private User setRole(User user) {

        Role role = new Role();
        role.setId(RoleEnum.ROLE_USER.getValue());
        user.setRole(role);
        return user;
    }

    private List<String> userLogins() {

        return userRepository.findAll().stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
    }


    @Override
    public UserDtoResponse updateUser(UpdateUserDto userDto, Long userId, Long positionId, Long courseId) {

        return Optional.of(update(getUser(userId), userMapper.toUser(userDto), positionId, courseId))
                .map(userRepository::save)
                .map(userMapper::toUserResponseDto)
                .orElseThrow();
    }

    private User update(User user, User updateUser, Long positionId, Long courseId) {

        if (!updateUser.getName().isBlank()) {
            user.setName(updateUser.getName());
        }

        if (!updateUser.getLastName().isBlank()) {
            user.setLastName(updateUser.getLastName());
        }

        if (!updateUser.getPassword().isBlank()) {
            user.setPassword(updateUser.getPassword());
        }

        if (positionId == null) {

            user.setPosition(null);
        } else {

            user.setPosition(positionService.getPosition(positionId));
        }

        if (courseId != null) {

            Course course = courseService.getCourse(courseId);

            if (user.getCourses()
                    .stream()
                    .noneMatch(item -> item.equals(course))) {

                user.getCourses().add(course);
            }
        }

        return user;
    }


    @Override
    public void deleteUser(Long id) {

        userRepository.delete(getUser(id));
        sendMessage(String.valueOf(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PagesDtoResponse<UserDtoResponse> getAll(int size, int page, String sort) {

        Page<UserDtoResponse> allUsers = userRepository
                .findAll(pagesService.getPage(size, page, sort))
                .map(userMapper::toUserResponseDto);

        if (allUsers.isEmpty()) {

            throw new NoDataFoundException("Users not found");
        }
        return pagesService.getPagesDtoResponse(size, page, sort, allUsers.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public PagesDtoResponse<UserDtoResponse> getUsersByFilter(String userName,
                                                              String userLogin,
                                                              String userLastName,
                                                              String position,
                                                              String course,
                                                              int size,
                                                              int page,
                                                              String sort) {

        Page<UserDtoResponse> usersFilter = userRepository
                .findAll(getSpecification(userName, userLogin, userLastName, position, course)
                        , pagesService.getPage(size, page, sort)).map(userMapper::toUserResponseDto);


        if (usersFilter.isEmpty()) {

            throw new NoDataFoundException("Users not found");
        }

        return pagesService.getPagesDtoResponse(size, page, sort, usersFilter.getContent());
    }

    private Specification<User> getSpecification(String userName,
                                                 String userLogin,
                                                 String userLastName,
                                                 String position,
                                                 String course) {

        return UserSpecifications.likeName(userName)
                .and(UserSpecifications.likeLogin(userLogin))
                .and(UserSpecifications.likeLastName(userLastName))
                .and(UserSpecifications.likePosition(position))
                .and(UserSpecifications.likeCourse(course));
    }

    @Override
    public User getUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("user by id " + id + " not found"));
    }

    @Override
    public UserDtoForAuthResponse findByLogin(String login) {
        return Optional
                .ofNullable(userRepository.findByLogin(login))
                .map(userMapper::userDtoForAuthResponse)
                .orElseThrow(
                        () -> new NoDataFoundException("user with login " + login + " does not exist")
                );
    }

    private void sendMessage(String message) {

        kafkaTemplate.send("delete", message);
    }
}
