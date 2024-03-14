package com.MyMiniProject.MiniProject.Models.Responses;

import com.MyMiniProject.MiniProject.Entities.Permission;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoleResponse {

    private Long id;
    private String roleName;

    private List<Permission> permissions ;

}
