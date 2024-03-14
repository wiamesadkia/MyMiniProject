package com.MyMiniProject.MiniProject.Mappers;

import com.MyMiniProject.MiniProject.Entities.Category;
import com.MyMiniProject.MiniProject.Entities.Product;
import com.MyMiniProject.MiniProject.Models.Requests.CategoryRequest;
import com.MyMiniProject.MiniProject.Models.Requests.CategoryUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.CategoryResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CategoryMapper extends ApplicationMapper<CategoryRequest, CategoryResponse, Category>{

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "id", ignore = true)
    Category responseToEntity(Category category);

    void updateEntity(CategoryUpdateRequest request, @MappingTarget Category entity);
}
