package com.example.kunuz.mapper;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
public interface ArticleShortInfo {
    String getId();

    String getTitle();

    String getDescription();

    Integer getAttachId();

    LocalDateTime getPublished_date();
}
