package com.MyMiniProject.MiniProject.Services.Impl;

import com.MyMiniProject.MiniProject.Entities.Product;
import com.MyMiniProject.MiniProject.Entities.User;
import com.MyMiniProject.MiniProject.Exeptions.ResourceDuplicatedException;
import com.MyMiniProject.MiniProject.Exeptions.ResourceNotFoundException;
import com.MyMiniProject.MiniProject.Mappers.ProductMapper;
import com.MyMiniProject.MiniProject.Mappers.UserMapper;
import com.MyMiniProject.MiniProject.Models.Requests.ProductRequest;
import com.MyMiniProject.MiniProject.Models.Requests.ProductUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.ProductResponse;
import com.MyMiniProject.MiniProject.Repositories.ProductRepository;
import com.MyMiniProject.MiniProject.Repositories.UserRepository;
import com.MyMiniProject.MiniProject.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse add(ProductRequest request) {
        // Check if product with the same name already exists
        if (request.getProductName() != null && productRepository.findByProductName(request.getProductName()).isPresent())
            throw new ResourceDuplicatedException("productName", "id", request.getProductName());

        // Create a new Product entity and populate it with data from the request
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());

        // Save the new product to the database
        productRepository.save(product);

        // Return the response with the newly added product details
        return ProductMapper.INSTANCE.entityToResponse(product);
    }

    @Override
    public List<ProductResponse> getAll() {

        List<Product> products = productRepository.findAll();
        return ProductMapper.INSTANCE.listToResponseList(products);
    }

    @Override
    public ProductResponse get(Long id) {
        // Retrieve the product from the database by its ID
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", id.toString()));

        // Create a new ProductResponse object and populate it with data from the Product entity
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setProductName(product.getProductName());
        response.setPrice(product.getPrice());
        response.setDescription(product.getDescription());

        // Return the product response
        return response;
    }

    @Override
    public ProductResponse update(ProductUpdateRequest request, Long id) {

        // Find the product by id
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", id.toString()));

        // Check if the updated product name already exists for another product
        if (request.getProductName() != null) {
            Optional<Product> productByName = productRepository.findByProductName(request.getProductName());
            if (productByName.isPresent() && !productByName.get().getId().equals(id))
                throw new ResourceDuplicatedException("product", "productName", request.getProductName());
        }

        // Update the product entity with the new data
        if (request.getProductName() != null)
            product.setProductName(request.getProductName());
        if (request.getPrice() != null)
            product.setPrice(request.getPrice());
        if (request.getDescription() != null)
            product.setDescription(request.getDescription());

        // Save the changes to the database
        productRepository.save(product);

        // Return the updated product response
        return ProductMapper.INSTANCE.entityToResponse(product);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", id.toString()));
        productRepository.delete(product);

    }
}
