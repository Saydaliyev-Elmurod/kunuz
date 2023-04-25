package com.example.kunuz.entity;

import com.example.kunuz.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Table
@Entity(name = "article")
public class ArticleEntity {
    @Id
    private String id;
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false, columnDefinition = "text")
    private String description;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    @Column(name = "shared_count")
    private Integer sharedCount = 0;
    @Column(name = "image_id")
    private Integer imageId;
//    @Column(name = "region_id", insertable = false, updatable = false)
//    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private RegionEntity region;
//    @Column(name = "category_id", insertable = false, updatable = false)
//    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
//    @Column(name = "moderator_id", insertable = false, updatable = false)
//    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id")
    private ProfileEntity moderator;
//    @Column(name = "publisher_id", insertable = false, updatable = false)
//    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private ProfileEntity publisher;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    //    @Column(name = "type_id", nullable = false)
//    private Integer typeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private ArticleTypeEntity type;
    @Column(name = "view_count")
    private Integer viewCount = 0;
    @Column(name = "visible")
    private Boolean visible = true;
}
