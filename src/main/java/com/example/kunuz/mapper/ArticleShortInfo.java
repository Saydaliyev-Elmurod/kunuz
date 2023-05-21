package com.example.kunuz.mapper;


import java.time.LocalDateTime;
public interface ArticleShortInfo {
    String getId();

    String getTitle();

    String getDescription();

    String getAttachId();

    LocalDateTime getPublished_date();
}
