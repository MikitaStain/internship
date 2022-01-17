package by.innowise.internship.util.impl;

import by.innowise.internship.exceptions.DuplicateException;
import by.innowise.internship.exceptions.EmailInvalidException;
import by.innowise.internship.exceptions.NotParameterException;
import by.innowise.internship.util.Validation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class ValidationImpl implements Validation {

    private final String REG_EMAIL = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";

    @Override
    public void checkParameter(String name) {

        if (name.isBlank()) {

            throw new NotParameterException("Parameter (" + name + ") invalid");
        }
    }

    @Override
    public void checkEmailValid(String email) {

        if (!Pattern.matches(REG_EMAIL, email)) {

            throw new EmailInvalidException("Email " + email + " invalid!!!!");
        }
    }

    @Override
    public void checkDuplicateParameter(List<String> items, String newParameter) {

        if (items.stream()
                .anyMatch(item -> item.equals(newParameter))) {

            throw new DuplicateException("Parameter " + newParameter + " already exists");
        }
    }
}
