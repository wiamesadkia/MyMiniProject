package com.MyMiniProject.MiniProject.Services;

import com.MyMiniProject.MiniProject.Models.Requests.CategoryRequest;
import com.MyMiniProject.MiniProject.Models.Requests.CategoryUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.CategoryResponse;

public interface CategoryService extends CrudServices<CategoryRequest, CategoryResponse, CategoryUpdateRequest,Long>{
}
