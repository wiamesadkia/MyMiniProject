package com.MyMiniProject.MiniProject.Services;

import com.MyMiniProject.MiniProject.Models.Requests.ProductRequest;
import com.MyMiniProject.MiniProject.Models.Requests.ProductUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.ProductResponse;

public interface ProductService extends CrudServices<ProductRequest, ProductResponse, ProductUpdateRequest, Long>{

}
