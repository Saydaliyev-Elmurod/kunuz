package com.example.kunuz.service.article;

import com.example.kunuz.dto.article.ArticleTypeDTO;
import com.example.kunuz.entity.ArticleTypeEntity;
import com.example.kunuz.enums.LangEnum;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.repository.article.ArticleTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleTypeService {

    private final ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setNameUz(dto.getNameUz());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        articleTypeRepository.save(entity);
        return toDTO(entity);
    }

    public ArticleTypeDTO updateById(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = getById(dto.getId());
        filter(entity, dto);
        articleTypeRepository.save(entity);
        return toDTO(entity);
    }

    public Page<ArticleTypeDTO> getList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ArticleTypeEntity> entityList = articleTypeRepository.findAllByVisibility(true,pageable);
        return new PageImpl<>(toList(entityList.getContent()), pageable, entityList.getTotalElements());
    }

    public HashMap<Integer, String> getList(LangEnum name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ArticleTypeEntity> entityList = articleTypeRepository.findAllByVisibility(true,pageable);
        HashMap<Integer, String> map = new HashMap<>();
        entityList.getContent().stream().map(entity -> {
            switch (name) {
                case en -> map.put(entity.getId(), entity.getNameEn());
                case ru -> map.put(entity.getId(), entity.getNameRu());
                case uz -> map.put(entity.getId(), entity.getNameUz());
            }
            return map;
        }).collect(Collectors.toList());
        return map;
    }

    private ArticleTypeDTO toDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    private ArticleTypeEntity filter(ArticleTypeEntity entity, ArticleTypeDTO dto) {
        if (dto.getNameUz() != null) {
            entity.setNameUz(dto.getNameUz());
        }
        if (dto.getNameRu() != null) {
            entity.setNameRu(dto.getNameRu());
        }
        if (dto.getNameEn() != null) {
            entity.setNameEn(dto.getNameEn());
        }
        if (dto.getVisible() != null) {
            entity.setVisible(dto.getVisible());
        }
        return entity;
    }

    public ArticleTypeEntity getById(Integer id) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()|| !optional.get().getVisible()){
            throw new ItemNotFoundException("Item not found");
        }
        return optional.get();
    }


    public void deleteById(Integer id) {
        articleTypeRepository.updateVisible(id);
    }


    private List<ArticleTypeDTO> toList(List<ArticleTypeEntity> entityList) {
        List<ArticleTypeDTO> dtoList = new ArrayList<>();
        entityList.forEach(articleTypeEntity -> dtoList.add(toDTO(articleTypeEntity)));
        return dtoList;
    }
}
