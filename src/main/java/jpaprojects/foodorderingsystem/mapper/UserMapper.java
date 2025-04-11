package jpaprojects.foodorderingsystem.mapper;

import jpaprojects.foodorderingsystem.dto.request.UserRegisterRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.UserResponseDTO;
import jpaprojects.foodorderingsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // <-- vacib!
public interface UserMapper {

    @Mapping(target = "role", constant = "ADMIN") // default role
    User toEntity(UserRegisterRequestDTO dto);

    UserResponseDTO toDto(User user);
}
