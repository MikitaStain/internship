package by.innowise.internship.util;

import java.util.List;

public interface Validation {

    void checkEmailValid(String email);

    void checkDuplicateParameter(List<String> items, String newParameter);

    void checkParameter(String name);

}
