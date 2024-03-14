package com.MyMiniProject.MiniProject.Models.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String username;

    private String password;

    private int isActive;
}
