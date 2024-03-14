package com.MyMiniProject.MiniProject.Services.Impl;

import com.MyMiniProject.MiniProject.Entities.User;
import com.MyMiniProject.MiniProject.Exeptions.ResourceDuplicatedException;
import com.MyMiniProject.MiniProject.Exeptions.ResourceNotFoundException;
import com.MyMiniProject.MiniProject.Mappers.UserMapper;
import com.MyMiniProject.MiniProject.Models.Requests.UserRequest;
import com.MyMiniProject.MiniProject.Models.Requests.UserUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.UserResponse;
import com.MyMiniProject.MiniProject.Repositories.UserRepository;
import com.MyMiniProject.MiniProject.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class  UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserResponse add(UserRequest request) {

        if (request.getUsername() != null && userRepository.findByUsername(request.getUsername()).isPresent())
            throw new ResourceDuplicatedException("UserName", "id", request.getUsername());

        User user = UserMapper.INSTANCE.requestToEntity(request);
        user.setUsername(request.getUsername()); // Set username
        user.setPassword(request.getPassword()); // Set password
        userRepository.save(user);

        return UserMapper.INSTANCE.entityToResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {

        return UserMapper.INSTANCE.listToResponseList(userRepository.findAll());
    }

    @Override
    public UserResponse get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id.toString()));
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setPassword(user.getPassword());

        return response;
    }

    @Override
    public UserResponse update(UserUpdateRequest request, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id.toString()));

        // Check if username is being updated and handle uniqueness
        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            Optional<User> userByUserName = userRepository.findByUsername(request.getUsername());
            if (userByUserName.isPresent() && !userByUserName.get().getId().equals(id))
                throw new ResourceDuplicatedException("user", "username", request.getUsername());
            user.setUsername(request.getUsername());
        }
        // Update password if provided
        if (request.getUsername() != null)
            user.setUsername(request.getUsername());
        if (request.getPassword() != null)
            user.setPassword(request.getPassword());
        if (request.getPassword() != null)
            user.setPassword(request.getPassword());

        // Save changes
        userRepository.save(user);

        // Prepare and return the response
        return UserMapper.INSTANCE.entityToResponse(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id.toString()));
        userRepository.delete(user);


    }

}
