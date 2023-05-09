package com.example.kunuz.repository.article;

import com.example.kunuz.entity.ArticleTypeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity, Integer> {
   Page<ArticleTypeEntity> findAll(Pageable pageable);
   @Modifying
   @Transactional
   @Query("update ArticleTypeEntity set  visible = false where  id =?1")
   void updateVisible(Integer id);

   @Query("SELECT a FROM ArticleTypeEntity a WHERE a.visible = ?1")
   Page<ArticleTypeEntity> findAllByVisibility(Boolean visible, Pageable pageable);

}
