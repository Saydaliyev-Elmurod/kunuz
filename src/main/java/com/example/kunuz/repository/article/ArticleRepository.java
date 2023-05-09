package com.example.kunuz.repository.article;

import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.mapper.ArticleShortInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query("update ArticleEntity set visible = :visible where id = :id")
    int updateVisible(@Param("visible") Boolean visible, @Param("id") String id);


    @Transactional
    @Modifying
    @Query("update ArticleEntity set status = :status where id = :id")
    int updateStatus(@Param("status") ArticleStatus status, @Param("id") String id);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:t_id and status =:status order by created_date desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfo> getTopN(@Param("t_id") Integer t_id, @Param("status") String status, @Param("limit") Integer limit);

    @Query("SELECT new ArticleEntity(id,title,description,attachId,publishedDate) From ArticleEntity " +
            "where status =:status and visible = true and id not in :idList " +
            " order by createdDate desc limit 8")
    List<ArticleEntity> find8ByTypeIdExceptIdLists(@Param("status") ArticleStatus status,
                                                   @Param("idList") List<String> idList);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:t_id and status ='PUBLISHED' and a.id<>:a_id order by created_date desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfo> getTopNWithoutId(@Param("t_id") Integer t_id, @Param("a_id") String a_id, @Param("limit") Integer limit);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where status ='PUBLISHED'  order by view_count desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfo> getTopNRead(@Param("limit") Integer limit);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:t_id and a.region_id =:r_id and status ='PUBLISHED'  order by created_date desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfo> getTopNTypeAndRegion(@Param("t_id") Integer t_id, @Param("r_id") Integer r_id, @Param("limit") Integer limit);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where a.region_id =:r_id and status ='PUBLISHED'  order by created_date desc  ",
            countQuery = "SELECT count(*) from article ", nativeQuery = true)
    Page<ArticleShortInfo> getArticleByRegion(@Param("r_id") Integer regionId, Pageable pageable);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where a.category_id =:c_id and status ='PUBLISHED'  order by created_date desc  ",
            countQuery = "SELECT count(*) from article ", nativeQuery = true)
    Page<ArticleShortInfo> getArticleByCategory(@Param("c_id") Integer categoryId, Pageable pageable);

    @Query("SELECT  new ArticleEntity(ae.id,ae.title,ae.description,ae.attachId,ae.publishedDate) from ArticleEntity as ae inner join ArticleTagEntity as at on ae.id=at.articleId  where at.tagId=?1 ")
    List<ArticleEntity> getByTagName(Integer id);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a inner join article_tag at on a.id=at.article_id where at.tag_id = :t_id  ", nativeQuery = true)
    List<ArticleShortInfo> getByTagNameNative(@Param("t_id") Integer t_id);

    @Modifying
    @Transactional
    @Query("update ArticleEntity set viewCount = viewCount+1 where id=?1")
    void increaseViewCount(String articleId);

    @Query("select viewCount from ArticleEntity  where id=?1")
    int getViewCount(String articleId);
    @Modifying
    @Transactional
    @Query("update ArticleEntity set sharedCount = sharedCount+1 where id=?1")
    void increaseShareCount(String articleId);
    @Query("select sharedCount from ArticleEntity  where id=?1")
    int getShareCount(String articleId);

}


