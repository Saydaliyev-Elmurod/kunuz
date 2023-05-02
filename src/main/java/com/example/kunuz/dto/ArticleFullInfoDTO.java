package com.example.kunuz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleFullInfoDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private  Integer sharedCount;
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private Integer likeCount;
    private List<TagDTO> tagList;
    private AttachDTO attach;
    private RegionDTO region;
    private CategoryDTO category;
    private ArticleTypeDTO type;
}
