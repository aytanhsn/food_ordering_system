package jpaprojects.foodorderingsystem.mapper;

import jpaprojects.foodorderingsystem.dtos.request.UserRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.UserResponseDTO;
import jpaprojects.foodorderingsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toEntity(UserRequestDTO dto);
    @Mapping(target = "id", source = "id")
    UserResponseDTO toDto(User entity);
}
