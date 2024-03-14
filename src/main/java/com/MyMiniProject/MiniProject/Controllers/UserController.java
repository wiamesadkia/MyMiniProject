package com.MyMiniProject.MiniProject.Controllers;

import com.MyMiniProject.MiniProject.Models.Requests.UserRequest;
import com.MyMiniProject.MiniProject.Models.Requests.UserUpdateRequest;
import com.MyMiniProject.MiniProject.Models.Responses.UserResponse;
import com.MyMiniProject.MiniProject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_CREATE')")
    public ResponseEntity<UserResponse> add(@RequestBody UserRequest request) {
        UserResponse userResponse = userService.add(request);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_READ')")
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> userResponses = userService.getAll();
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ')")
    public ResponseEntity<UserResponse> get(@PathVariable Long id) {
        UserResponse userResponse = userService.get(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_UPDATE')")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        UserResponse userResponse = userService.update(request, id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

   /* public UserResponse addRoleToUser(Long id_user, Long id_role) {
        Optional<User> findUser = userRepository.findById(id_user);
        Optional<Role> findRole = roleRepository.findById(id_role);
        String message = messageSource.getMessage("entity.not.found", null, "No MESSAGE", LocaleContextHolder.getLocale());
        if (!findUser.isPresent()) {

            logger.error(message, findUser.get().getClass().getSimpleName(), id_user);
            throw new ResourceNotFoundException(User.class.getSimpleName(), "id_user", String.valueOf(id_user));
        }
        if (!findRole.isPresent()) {
            logger.error(message, findUser.get().getClass().getSimpleName(), id_role);
            throw new ResourceNotFoundException(Role.class.getSimpleName(), "id_role", String.valueOf(id_role));
        }
        User user = findUser.get();
        user.getRoles().add(
                findRole.get()
        );
        return UserMapper.INSTANCE.entityToResponse(user);
    }*/
}

