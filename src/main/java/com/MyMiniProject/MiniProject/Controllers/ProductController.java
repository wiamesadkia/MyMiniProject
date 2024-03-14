package com.MyMiniProject.MiniProject.Controllers;


import com.MyMiniProject.MiniProject.Models.Requests.ProductRequest;
import com.MyMiniProject.MiniProject.Models.Requests.ProductUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.ProductResponse;
import com.MyMiniProject.MiniProject.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN','MOD','USER')")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_CREATE', 'MOD_CREATE' , 'USER_CREATE')")
    public ResponseEntity<ProductResponse> add(@RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.add(request);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ', 'MOD_READ' , 'USER_READ')")
    public ResponseEntity<List<ProductResponse>> getAll() {
        List<ProductResponse> productResponses = productService.getAll();
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ', 'MOD_READ' , 'USER_READ')")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        ProductResponse productResponse = productService.get(id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_UPDATE', 'MOD_UPDATE' , 'USER_UPDATE')")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        ProductResponse productResponse = productService.update(request, id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_DELETE', 'MOD_DELETE' , 'USER_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
