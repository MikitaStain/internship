package by.innowise.internship.service.impl;

import by.innowise.internship.dto.UserCreateRequestDto;
import by.innowise.internship.dto.UserDto;
import by.innowise.internship.dto.UserDtoForFilter;
import by.innowise.internship.dto.buildDto.Builders;
import by.innowise.internship.dto.responseDto.PagesDtoResponse;
import by.innowise.internship.dto.responseDto.UserDtoResponse;
import by.innowise.internship.entity.Course;
import by.innowise.internship.entity.User;
import by.innowise.internship.exceptions.NoCreateException;
import by.innowise.internship.exceptions.NoDataFoundException;
import by.innowise.internship.exceptions.ResourceNotFoundException;
import by.innowise.internship.mappers.UserMapper;
import by.innowise.internship.repository.dao.UserRepository;
import by.innowise.internship.repository.filter.FilterForSearchUsers;
import by.innowise.internship.service.CourseService;
import by.innowise.internship.service.PositionService;
import by.innowise.internship.service.UserService;
import by.innowise.internship.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    private final PositionService positionService;
    private final CourseService courseService;
    private final Validation validation;
    private final Builders builders;
    private final FilterForSearchUsers filterForSearchUsers;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PagesService pagesService,
                           PositionService positionService,
                           CourseService courseService,
                           Validation validation, Builders builders, FilterForSearchUsers filterForSearchUsers) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.pagesService = pagesService;
        this.positionService = positionService;
        this.courseService = courseService;
        this.validation = validation;
        this.builders = builders;
        this.filterForSearchUsers = filterForSearchUsers;
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
                .map(userRepository::save)
                .map(User::getId)
                .orElseThrow(() -> new NoCreateException("User can not created"));
    }

    private List<String> userLogins() {

        return userRepository.findAll().stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
    }


    @Override
    public UserDtoResponse updateUser(UserDto userDto, Long userId, Long positionId, Long courseId) {

        return Optional.of(update(getUser(userId), userMapper.toEntity(userDto), positionId, courseId))
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

    @Override
    public List<UserDtoResponse> getUsersByFilter(String userName,
                                                  String userLogin,
                                                  String userLastName,
                                                  String position,
                                                  String course,
                                                  int size,
                                                  int page,
                                                  String sort) {

        UserDtoForFilter userDtoForFilter = builders.buildUserDtoForFilter(
                userName, userLogin, userLastName, position, course);

        List<User> userByFilter = filterForSearchUsers.findUserByFilter(userDtoForFilter);
        List<UserDtoResponse> collect = userByFilter.stream()
                .map(user -> userMapper.toUserResponseDto(user))
                .collect(Collectors.toList());

        return collect;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User getUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("user by id " + id + " not found"));
    }
}
