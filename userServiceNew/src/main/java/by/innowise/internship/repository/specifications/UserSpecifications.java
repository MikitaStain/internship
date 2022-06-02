package by.innowise.internship.repository.specifications;

import by.innowise.internship.entity.Course_;
import by.innowise.internship.entity.Position_;
import by.innowise.internship.entity.User;
import by.innowise.internship.entity.User_;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Optional.ofNullable;

public class UserSpecifications {

    protected UserSpecifications() {
    }

    public static Specification<User> likeName(String name) {

        return (root, query, criteriaBuilder) -> ofNullable(name)
                .map(nameUser -> criteriaBuilder.equal(root.get(User_.NAME), nameUser))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<User> likeLastName(String lastName) {

        return (root, query, criteriaBuilder) -> ofNullable(lastName)
                .map(lastNameUser -> criteriaBuilder.equal(root.get(User_.LAST_NAME), lastNameUser))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<User> likeLogin(String login) {

        return (root, query, criteriaBuilder) -> ofNullable(login)
                .map(loginUser -> criteriaBuilder.equal(root.get(User_.LOGIN), loginUser))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<User> likePosition(String position){

        return (root, query, criteriaBuilder) -> ofNullable(position)
                .map(positionUser -> criteriaBuilder.equal(root.get(User_.POSITION).get(Position_.NAME),positionUser))
                .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<User> likeCourse(String course){

        return (root, query, criteriaBuilder) -> ofNullable(course)
                .map(courseUser -> criteriaBuilder.equal(root.join(User_.COURSES).get(Course_.NAME),courseUser))
                .orElseGet(criteriaBuilder::conjunction);
    }

}
