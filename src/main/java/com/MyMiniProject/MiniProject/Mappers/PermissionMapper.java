package com.MyMiniProject.MiniProject.Mappers;

import com.MyMiniProject.MiniProject.Entities.Permission;
import com.MyMiniProject.MiniProject.Models.Requests.PermissionRequest;
import com.MyMiniProject.MiniProject.Models.Responses.PermissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PermissionMapper extends ApplicationMapper<PermissionRequest, PermissionResponse, Permission>{

    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

}
