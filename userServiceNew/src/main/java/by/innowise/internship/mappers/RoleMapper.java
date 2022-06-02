package by.innowise.internship.mappers;

import by.innowise.internship.dto.responseDto.RoleDtoResponse;
import by.innowise.internship.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDtoResponse toDto(Role role);
}
