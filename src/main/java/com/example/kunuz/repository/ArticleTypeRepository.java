package com.example.kunuz.repository;

import com.example.kunuz.entity.ArticleTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {
   Page<ArticleTypeEntity> findAll(Pageable pageable);
}
