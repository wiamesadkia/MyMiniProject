package com.MyMiniProject.MiniProject.Models.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {

    private Long id;

    private String productName;

    private Float price;

    private String description;
}
