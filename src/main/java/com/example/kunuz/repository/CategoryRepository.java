package com.example.kunuz.repository;

import com.example.kunuz.entity.ArticleTypeEntity;
import com.example.kunuz.entity.CategoryEntity;
import com.example.kunuz.entity.RegionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Page<CategoryEntity> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update CategoryEntity set  visible = false where  id =?1")
    void updateVisible(Integer id);

    @Query("SELECT a FROM CategoryEntity a WHERE a.visible = ?1")
    Page<CategoryEntity> findAllByVisibility(Boolean visible, Pageable pageable);
}
