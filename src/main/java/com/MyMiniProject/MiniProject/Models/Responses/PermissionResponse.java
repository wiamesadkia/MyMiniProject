package com.MyMiniProject.MiniProject.Models.Responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionResponse {

    private Long id;
    private String namePermission;
}
