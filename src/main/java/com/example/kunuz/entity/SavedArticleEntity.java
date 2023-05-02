package com.example.kunuz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "saved_article")
@Entity
public class SavedArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDateTime createdDate=LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;
    @Column(name = "article_id")
    private String articleId;

    public SavedArticleEntity(Integer id, ArticleEntity article) {
        this.id = id;
        this.article = article;
    }

    public SavedArticleEntity() {
    }
}
