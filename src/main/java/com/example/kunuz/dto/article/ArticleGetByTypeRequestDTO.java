package com.example.kunuz.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleGetByTypeRequestDTO {
    private List<String> idList;
}
