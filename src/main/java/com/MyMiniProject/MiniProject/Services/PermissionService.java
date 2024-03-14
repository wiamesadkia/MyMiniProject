package com.MyMiniProject.MiniProject.Services;

import com.MyMiniProject.MiniProject.Entities.Permission;
import com.MyMiniProject.MiniProject.Models.Requests.PermissionRequest;
import com.MyMiniProject.MiniProject.Models.Responses.PermissionResponse;

public interface PermissionService extends CrudServices<PermissionRequest, PermissionResponse, PermissionRequest,Long>{
}
