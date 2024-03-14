package com.MyMiniProject.MiniProject.Repositories;

import com.MyMiniProject.MiniProject.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByCategoryName(String categoryName);
}
