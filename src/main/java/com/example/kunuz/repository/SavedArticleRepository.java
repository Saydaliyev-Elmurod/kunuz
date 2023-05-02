package com.example.kunuz.repository;

import com.example.kunuz.entity.SavedArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SavedArticleRepository extends JpaRepository<SavedArticleEntity, Integer> {


    @Query("select new SavedArticleEntity (id ,new ArticleEntity(article.id,article.title,article.description,article.attachId,article.publishedDate))  from SavedArticleEntity where profileId=?1")
    List<SavedArticleEntity> getList(Integer userId);

    @Query("from SavedArticleEntity where profileId=?1 and article.id=?2")
    SavedArticleEntity getByUserIdAndArticleId(int profileId,String articleId);
}
