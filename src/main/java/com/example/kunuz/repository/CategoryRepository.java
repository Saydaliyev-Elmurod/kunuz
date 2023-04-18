package com.example.kunuz.repository;

import com.example.kunuz.entity.ArticleTypeEntity;
import com.example.kunuz.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    Page<CategoryEntity> findAll(Pageable pageable);

}
