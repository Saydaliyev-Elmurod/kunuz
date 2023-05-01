package com.example.kunuz.service;

import com.example.kunuz.dto.ArticleDTO;
import com.example.kunuz.dto.TagDTO;
import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.entity.TagEntity;
import com.example.kunuz.exps.ItemAlreadyExistsException;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.repository.TagRepository;
import org.aspectj.apache.bcel.generic.IINC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagDTO dto) {
        TagEntity old = tagRepository.getByName(dto.getName());
        if (old!=null){
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
}
