package com.MyMiniProject.MiniProject.Models.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateRequest {

    private Long id;

    private String categoryName;
}
