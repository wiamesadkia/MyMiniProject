package com.MyMiniProject.MiniProject.Controllers;

import com.MyMiniProject.MiniProject.Models.Requests.CategoryRequest;
import com.MyMiniProject.MiniProject.Models.Requests.CategoryUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.CategoryResponse;
import com.MyMiniProject.MiniProject.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN','MOD')")
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_CREATE', 'MOD_CREATE')")
    public ResponseEntity<CategoryResponse> add(@RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.add(request);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ', 'MOD_READ')")
    public ResponseEntity<List<CategoryResponse>> getAll() {
        List<CategoryResponse> categoryResponses = categoryService.getAll();
        return new ResponseEntity<>(categoryResponses, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ', 'MOD_READ')")
    public ResponseEntity<CategoryResponse> get(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.get(id);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_UPDATE', 'MOD_UPDATE' )")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryUpdateRequest request) {
        CategoryResponse categoryResponse = categoryService.update(request, id);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_DELETE', 'MOD_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
