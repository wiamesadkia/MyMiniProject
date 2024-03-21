package com.MyMiniProject.MiniProject.Services.Impl;

import com.MyMiniProject.MiniProject.Entities.Role;
import com.MyMiniProject.MiniProject.Entities.User;
import com.MyMiniProject.MiniProject.Exeptions.ResourceNotFoundException;
import com.MyMiniProject.MiniProject.Mappers.UserMapper;
import com.MyMiniProject.MiniProject.Models.Requests.LoginRequest;
import com.MyMiniProject.MiniProject.Models.Requests.RegisterRequest;
import com.MyMiniProject.MiniProject.Models.Responses.LoginResponse;
import com.MyMiniProject.MiniProject.Models.Responses.UserResponse;
import com.MyMiniProject.MiniProject.Repositories.RoleRepository;
import com.MyMiniProject.MiniProject.Repositories.UserRepository;
import com.MyMiniProject.MiniProject.Services.AuthService;
import com.MyMiniProject.MiniProject.config.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserResponse register(RegisterRequest request) {
        // Fetch roles from the request and find them in the role repository.
        List<Role> roles = request.getRoles().stream()
                .map(role -> roleRepository.findByRoleName(role.getRoleName())
                        .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), "id_role", String.valueOf(role.getId()))))
                .collect(Collectors.toList());

        // Create the user entity with the associated roles and other fields, then save it to the database.
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        return UserMapper.INSTANCE.entityToResponse(user);
    }



    @Override
    public List<LoginResponse> login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        // Get the authenticated user
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<LoginResponse> loginResponses = new ArrayList<>();
            for (Role role : user.getRoles()) {
                var roleAccessToken = jwtService.generateTokenWithRole(role, user);
                var roleRefreshToken = jwtService.generateRefreshTokenWithRole(role, user);
                loginResponses.add(LoginResponse.builder()
                        .role(role.getRoleName())
                        .accessToken(roleAccessToken)
                        .refreshToken(roleRefreshToken)
                        .build());
            }
            return loginResponses;
        } else {
            return Collections.emptyList();
        }
    }
    @Override
    public UserResponse addRoleToUser(Long id_user, Long id_role) {
        Optional<User> findUser = userRepository.findById(id_user);
        Optional<Role> findRole = roleRepository.findById(id_role);
        if (!findUser.isPresent()) {
            logger.error("User with id {} not found", id_user);
            throw new ResourceNotFoundException(User.class.getSimpleName(), "id_user", String.valueOf(id_user));
        }
        if (!findRole.isPresent()) {
            logger.error("Role with id {} not found", id_role);
            throw new ResourceNotFoundException(Role.class.getSimpleName(), "id_role", String.valueOf(id_role));
        }
        User user = findUser.get();
        user.getRoles().add(
                findRole.get()
        );
        return UserMapper.INSTANCE.entityToResponse(user);
    }
    @Override
    public void deleteRoleFromUser(Long id_user, Long id_role) {
        Optional<User> findUser = userRepository.findById(id_user);
        Optional<Role> findRole = roleRepository.findById(id_role);
        if (!findUser.isPresent()) {
            logger.error("User with id {} not found", id_user);
            throw new ResourceNotFoundException(User.class.getSimpleName(), "id_user", String.valueOf(id_user));
        }
        if (!findRole.isPresent()) {
            logger.error("User with id {} not found", id_role);
            throw new ResourceNotFoundException(Role.class.getSimpleName(), "id_role", String.valueOf(id_role));
        }
        User appUser = findUser.get();
        appUser.getRoles().remove(findRole.get());
    }
    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        final String roleName;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        roleName = jwtService.extractRoleFromToken(refreshToken);
        Optional<Role> findRole = roleRepository.findByRoleName(roleName);
        if (username != null && findRole.isPresent()) {
            Role role = findRole.get();
            var userDetails = userRepository.findByUsername(username).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateRefreshTokenWithRole(role, userDetails);
                var authResponse = LoginResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

    }


    @Override
    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public Optional<Boolean> validateToken(String token) {
        boolean isValid = jwtService.isTokenValid(token);
        return Optional.of(isValid);
    }



}
