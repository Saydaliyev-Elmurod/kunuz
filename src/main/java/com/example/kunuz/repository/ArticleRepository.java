package com.example.kunuz.repository;

import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.mapper.ArticleShortInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    Optional<ArticleEntity> findByTitle(String title);

    Optional<ArticleEntity> findById(String id);

    @Transactional
    @Modifying
    @Query("update article set visible = :visible where id = :id")
    int updateVisible(@Param("visible") Boolean visible, @Param("id") String id);


    @Transactional
    @Modifying
    @Query("update article set status = :status where id = :id")
    int updateStatus(@Param("status") ArticleStatus status, @Param("id") String id);
    @Query(value = "SELECT a.id,a.title,a.description,a.image_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:t_id and status =:status order by created_date desc Limit 5", nativeQuery = true)
    List<ArticleShortInfo> getTop5(@Param("t_id") Integer t_id,@Param("status") String status);
    @Query(value = "SELECT a.id,a.title,a.description,a.image_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:t_id and status =:status order by created_date desc Limit 3", nativeQuery = true)
    List<ArticleShortInfo> getTop3(@Param("t_id") Integer t_id,@Param("status") String status);
}
