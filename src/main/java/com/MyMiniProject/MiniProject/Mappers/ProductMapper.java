package com.MyMiniProject.MiniProject.Mappers;

import com.MyMiniProject.MiniProject.Entities.Product;
import com.MyMiniProject.MiniProject.Models.Requests.ProductRequest;
import com.MyMiniProject.MiniProject.Models.Requests.ProductUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.ProductResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ProductMapper extends ApplicationMapper<ProductRequest, ProductResponse, Product>{

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    Product responseToEntity(Product product);

    void updateEntity(ProductUpdateRequest request, @MappingTarget Product entity);
}
