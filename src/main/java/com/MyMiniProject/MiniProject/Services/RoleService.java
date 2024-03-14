package com.MyMiniProject.MiniProject.Services;

import com.MyMiniProject.MiniProject.Entities.Role;
import com.MyMiniProject.MiniProject.Models.Requests.RoleRequest;
import com.MyMiniProject.MiniProject.Models.Responses.RoleResponse;

public interface RoleService extends  CrudServices<RoleRequest, RoleResponse, RoleRequest,Long>{

    RoleResponse addPermissionToRole(Long role_Id,Long permission_id);
    RoleResponse deletePermissionToRole(Long role_Id,Long permission_id);

}
