package com.MyMiniProject.MiniProject.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "products")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String productName;

    private Float price;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
