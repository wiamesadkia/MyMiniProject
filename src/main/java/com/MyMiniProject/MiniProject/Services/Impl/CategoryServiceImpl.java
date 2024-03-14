package com.MyMiniProject.MiniProject.Services.Impl;

import com.MyMiniProject.MiniProject.Entities.Category;
import com.MyMiniProject.MiniProject.Entities.Product;
import com.MyMiniProject.MiniProject.Entities.User;
import com.MyMiniProject.MiniProject.Exeptions.ResourceDuplicatedException;
import com.MyMiniProject.MiniProject.Exeptions.ResourceNotFoundException;
import com.MyMiniProject.MiniProject.Mappers.CategoryMapper;
import com.MyMiniProject.MiniProject.Mappers.ProductMapper;
import com.MyMiniProject.MiniProject.Mappers.UserMapper;
import com.MyMiniProject.MiniProject.Models.Requests.CategoryRequest;
import com.MyMiniProject.MiniProject.Models.Requests.CategoryUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.CategoryResponse;
import com.MyMiniProject.MiniProject.Models.Responses.ProductResponse;
import com.MyMiniProject.MiniProject.Repositories.CategoryRepository;
import com.MyMiniProject.MiniProject.Repositories.UserRepository;
import com.MyMiniProject.MiniProject.Services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse add(CategoryRequest request) {
        // Check if product with the same name already exists
        if (request.getCategoryName() != null && categoryRepository.findByCategoryName(request.getCategoryName()).isPresent())
            throw new ResourceDuplicatedException("categoryName", "id", request.getCategoryName());

        // Create a new Product entity and populate it with data from the request
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());

        // Save the new product to the database
        categoryRepository.save(category);

        // Return the response with the newly added product details
        return CategoryMapper.INSTANCE.entityToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAll() {

        return CategoryMapper.INSTANCE.listToResponseList(categoryRepository.findAll());
    }

    @Override
    public CategoryResponse get(Long id) {
        // Retrieve the cat from the database by its ID
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id.toString()));

        // Create a new catResponse object and populate it with data from the Product entity
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setCategoryName(category.getCategoryName());

        // Return the cat response
        return response;
    }

    @Override
    public CategoryResponse update(CategoryUpdateRequest request, Long id) {
        // Find the product by id
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id.toString()));

        // Check if the updated product name already exists for another product
        if (request.getCategoryName() != null) {
            Optional<Category> categoryByName = categoryRepository.findByCategoryName(request.getCategoryName());
            if (categoryByName.isPresent() && !categoryByName.get().getId().equals(id))
                throw new ResourceDuplicatedException("category", "categoryName", request.getCategoryName());
        }

        // Update the product entity with the new data
        if (request.getCategoryName() != null)
            category.setCategoryName(request.getCategoryName());

        // Save the changes to the database
        categoryRepository.save(category);

        // Return the updated product response
        return CategoryMapper.INSTANCE.entityToResponse(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id.toString()));
        categoryRepository.delete(category);


    }
}
