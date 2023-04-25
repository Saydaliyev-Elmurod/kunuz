package com.example.kunuz.dto;

import com.example.kunuz.entity.ArticleTypeEntity;
import com.example.kunuz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount = 0;
    private Integer imageId;
    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Integer typeId;
    private Integer viewCount = 0;
    private Boolean visible =false;
}
