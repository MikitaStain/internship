package by.innowise.internship.repository.filter;

import by.innowise.internship.dto.UserDtoForFilter;
import by.innowise.internship.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterForSearchUsers {

    private final EntityManager entityManager;

    public FilterForSearchUsers(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> findUserByFilter(UserDtoForFilter userDtoForFilter) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> users = query.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        if (userDtoForFilter.getName() != null) {

            predicates.add(criteriaBuilder.equal(users.get("name"), userDtoForFilter.getName()));

        }
        if (userDtoForFilter.getLastName() != null) {

            predicates.add(criteriaBuilder.equal(users.get("lastName"), userDtoForFilter.getLastName()));

        }
        if (userDtoForFilter.getLogin() != null) {

            predicates.add(criteriaBuilder.equal(users.get("login"), userDtoForFilter.getLogin()));

        }
        if (userDtoForFilter.getPositions() != null) {

        }
        if (userDtoForFilter.getCourses() != null) {

        }

        query.where(predicates.toArray(new Predicate[0]));
        List<User> resultList = entityManager.createQuery(query).getResultList();
        System.out.println(resultList);

        return resultList;
    }
}
