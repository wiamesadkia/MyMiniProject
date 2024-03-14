package com.MyMiniProject.MiniProject.Models.Requests;

import com.MyMiniProject.MiniProject.Entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

    private String roleName;
    private List<Permission> permissions ;

}
