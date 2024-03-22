package com.MyMiniProject.MiniProject.Services;

import com.MyMiniProject.MiniProject.Entities.User;
import com.MyMiniProject.MiniProject.Models.Requests.LoginRequest;
import com.MyMiniProject.MiniProject.Models.Requests.RegisterRequest;
import com.MyMiniProject.MiniProject.Models.Responses.LoginResponse;
import com.MyMiniProject.MiniProject.Models.Responses.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse addRoleToUser(Long id_user, Long id_role);

    void deleteRoleFromUser(Long id_user, Long id_role);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    Optional<User> getAuthenticatedUser();

    Optional<User> findByUserName(String username);

    Optional<Boolean> validateToken(String token);
}
