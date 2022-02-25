package by.innowise.internship.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RoleEnum {


    ROLE_ADMIN(1L),
    ROLE_USER(2L);


    private final Long value;

    public Long getValue() {
        return value;
    }
}
