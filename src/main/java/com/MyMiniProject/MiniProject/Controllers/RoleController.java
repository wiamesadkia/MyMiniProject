package com.MyMiniProject.MiniProject.Controllers;

import com.MyMiniProject.MiniProject.Models.Requests.RoleRequest;
import com.MyMiniProject.MiniProject.Models.Responses.RoleResponse;
import com.MyMiniProject.MiniProject.Services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public ResponseEntity<RoleResponse> add (@RequestBody RoleRequest request){
        return  new ResponseEntity<>(roleService.add(request), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<List<RoleResponse>> getAll(){
        return new ResponseEntity<>(roleService.getAll(),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_UPDATE')")
    public ResponseEntity<RoleResponse> update(@RequestBody RoleRequest request,@PathVariable Long id){
        return  new ResponseEntity<>(roleService.update(request,id),HttpStatus.OK);
    }

   /* @PostMapping("add-permission-to-role/{idRole}/{idPermission}")
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public ResponseEntity<RoleResponse> addPermissionToRole(@PathVariable Long idRole, @PathVariable Long idPermission){
        return  new ResponseEntity<>(roleService.addPermissionToRole(idRole,idPermission),HttpStatus.OK);
    }*/
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    public  ResponseEntity<String> delete(@PathVariable Long id){
        roleService.delete(id);
        return  new ResponseEntity<>("Role Deleted",HttpStatus.NO_CONTENT);
    }
 /*   @DeleteMapping("remove-permission-from-role/{idRole}/{idPermission}")
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public  ResponseEntity<RoleResponse > deletePermissionFromRole(@PathVariable Long idRole,@PathVariable Long idPermission){
        return  new ResponseEntity<>(roleService.deletePermissionToRole(idRole,idPermission),HttpStatus.OK);
    }*/
}
