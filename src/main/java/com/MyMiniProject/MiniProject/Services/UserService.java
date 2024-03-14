package com.MyMiniProject.MiniProject.Services;


import com.MyMiniProject.MiniProject.Models.Requests.LoginRequest;
import com.MyMiniProject.MiniProject.Models.Requests.UserRequest;
import com.MyMiniProject.MiniProject.Models.Requests.UserUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.LoginResponse;
import com.MyMiniProject.MiniProject.Models.Responses.UserResponse;

import java.util.List;

public interface UserService extends CrudServices<UserRequest, UserResponse, UserUpdateRequest, Long>{

}
