package com.MyMiniProject.MiniProject.Mappers;

import com.MyMiniProject.MiniProject.Entities.Product;
import com.MyMiniProject.MiniProject.Entities.User;
import com.MyMiniProject.MiniProject.Models.Requests.UserRequest;
import com.MyMiniProject.MiniProject.Models.Requests.UserUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.UserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)

public interface UserMapper extends ApplicationMapper<UserRequest, UserResponse, User>{

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    User responseToEntity(User user);

    void updateEntity(UserUpdateRequest request, @MappingTarget User entity);

}
