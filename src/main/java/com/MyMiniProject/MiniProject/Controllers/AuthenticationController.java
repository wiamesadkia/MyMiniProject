package com.MyMiniProject.MiniProject.Controllers;

import com.MyMiniProject.MiniProject.Models.Requests.LoginRequest;
import com.MyMiniProject.MiniProject.Models.Requests.RegisterRequest;
import com.MyMiniProject.MiniProject.Models.Responses.LoginResponse;
import com.MyMiniProject.MiniProject.Models.Responses.UserResponse;
import com.MyMiniProject.MiniProject.Services.AuthService;
import com.MyMiniProject.MiniProject.Services.Impl.AuthServiceImp;
import com.MyMiniProject.MiniProject.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthService authService;
    private final AuthServiceImp userServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<List<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(authService.register(registerRequest), HttpStatus.CREATED);
    }

    @PostMapping("/add-role/{userId}/{roleId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN_CREATE')")
    public ResponseEntity<UserResponse> addRoleToUser(@PathVariable Long userId,
                                                      @PathVariable Long roleId) {
        return new ResponseEntity<>(userServiceImpl.addRoleToUser(userId, roleId), HttpStatus.CREATED);
    }

    @PostMapping("/delete-role/{userId}/{roleId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN_CREATE')")
    public ResponseEntity<String> deleteRoleFromUser(@PathVariable Long userId,
                                                     @PathVariable Long roleId) {
        userServiceImpl.deleteRoleFromUser(userId, roleId);
        return new ResponseEntity<>("the role is deleted!", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        userServiceImpl.refreshToken(request, response);
    }

    @PostMapping("/validate-token")
    public boolean validateToken(HttpServletRequest request
    ) throws IOException {
        String token = request.getHeader("token");
        return jwtService.isTokenValid(token);
    }

}
