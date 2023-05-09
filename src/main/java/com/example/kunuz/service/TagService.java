package com.example.kunuz.service;

import com.example.kunuz.dto.TagDTO;
import com.example.kunuz.entity.ArticleTagEntity;
import com.example.kunuz.entity.TagEntity;
import com.example.kunuz.exps.ItemAlreadyExistsException;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.repository.TagRepository;
import com.example.kunuz.repository.article.ArticleTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;

    public TagDTO create(TagDTO dto) {
        TagEntity old = tagRepository.getByName(dto.getName());
        if (old != null) {
            throw new ItemAlreadyExistsException("Item already exist");
        }
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        entity = tagRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public TagDTO update(Integer tagId, TagDTO dto) {
        TagEntity entity = getById(tagId);
        entity.setName(dto.getName());
        return toDTO(tagRepository.save(entity));
    }

    public TagEntity getById(Integer tagId) {
        TagEntity entity = tagRepository.findById(tagId).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
        return entity;
    }

    public TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        return dto;
    }

    public List<TagDTO> toList(List<TagEntity> list) {
        List<TagDTO> dtoArrayList = new ArrayList<>();
        list.forEach(tag -> {
            dtoArrayList.add(toDTO(tag));
        });
        return dtoArrayList;
    }

    public void delete(Integer tagId) {
        tagRepository.deleteById(tagId);
    }

    public Page<TagDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TagEntity> entityPage = tagRepository.findAll(pageable);
        return new PageImpl<>(toList(entityPage.getContent()), pageable, entityPage.getTotalElements());
    }

    public void addTagToArticle(String articleId, Integer tagId) {
        ArticleTagEntity entity = new ArticleTagEntity();
        entity.setArticleId(articleId);
        entity.setTagId(tagId);
        articleTagRepository.save(entity);
    }

    public TagDTO getByName(String tagName) {
        return toDTO(tagRepository.getByName(tagName));
    }
}
