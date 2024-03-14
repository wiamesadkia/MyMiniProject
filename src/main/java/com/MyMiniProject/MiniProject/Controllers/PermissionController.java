package com.MyMiniProject.MiniProject.Controllers;

import com.MyMiniProject.MiniProject.Models.Requests.PermissionRequest;
import com.MyMiniProject.MiniProject.Models.Responses.PermissionResponse;
import com.MyMiniProject.MiniProject.Services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public ResponseEntity<PermissionResponse> add (@RequestBody PermissionRequest request){
        return  new ResponseEntity<>(permissionService.add(request), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public ResponseEntity<List<PermissionResponse>> getAllRoles(){
        return new ResponseEntity<>(permissionService.getAll(),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public ResponseEntity<PermissionResponse> update(@RequestBody PermissionRequest request, @PathVariable Long id){
        return  new ResponseEntity<>(permissionService.update(request,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public  ResponseEntity<String> deleteRole(@PathVariable Long id){
        permissionService.delete(id);
        return  new ResponseEntity<>("Permission Deleted",HttpStatus.NO_CONTENT);
    }
}

