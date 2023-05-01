package com.example.kunuz.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ArticleDTO {
    private String id;
    @Size(min =15, max = 225, message = "Title must be between 10 and 225 characters")
    private String title;
    @Size(min =15, message = "Description must be greater than 15 characters")
    private String description;
    @Size(min =20, message = "Content must be greater than 20 characters")
    private String content;
    private String attachId;
    @Positive(message = "Region Id must be greater than zero")
    private Integer regionId;
    @Positive(message = "Category Id must be greater than zero")
    private Integer categoryId;
    @Positive(message = "Type Id must be greater than zero")
    private Integer typeId;
}
