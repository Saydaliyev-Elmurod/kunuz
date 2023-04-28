package com.example.kunuz.service;

import com.example.kunuz.dto.ArticleShortInfoDTO;
import com.example.kunuz.entity.CategoryEntity;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.entity.RegionEntity;
import com.example.kunuz.exps.MethodNotAllowedException;
import com.example.kunuz.mapper.ArticleShortInfo;
import com.example.kunuz.dto.ArticleDTO;
import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.exps.ItemAlreadyExistsException;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ProfileService profileService;

    public ArticleDTO create(ArticleDTO dto, Integer moderator_id) {
//        isValid(dto);
//        ArticleEntity entity = new ArticleEntity();
//        entity.setId(UUID.randomUUID().toString());
//        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
//        entity.setTitle(dto.getTitle());
//        entity.setDescription(dto.getDescription());
//        entity.setContent(dto.getContent());
//        entity.setSharedCount(dto.getSharedCount());
//        entity.setImageId(dto.getImageId());
//        entity.setRegion(regionService.getById(dto.getRegionId()));
//        entity.setCategory(categoryService.getById(dto.getCategoryId()));
//        entity.setModerator(profileService.getById(moderator_id));
//        entity.setType(articleTypeService.getById(dto.getTypeId()));
//        entity.setCreatedDate(LocalDateTime.now());
//        articleRepository.save(entity);
//        return toDTO(entity);
        // check
//        ProfileEntity moderator = profileService.get(moderId);
//        RegionEntity region = regionService.get(dto.getRegionId());
//        CategoryEntity category = categoryService.get(dto.getCategoryId());

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setModeratorId(moderator_id);
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        // type
        articleRepository.save(entity);
        return dto;
    }


    private ArticleDTO toDTO(ArticleEntity entity) {

        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());

        dto.setAttachId(entity.getAttachId());
        dto.setRegionId(entity.getRegion().getId());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setModeratorId(entity.getModerator().getId());
//        dto.setPublisherId(entity.getPublisher().getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setPublishedDate(entity.getPublishedDate());
        dto.setVisible(entity.getVisible());
        dto.setViewCount(entity.getViewCount());
        return dto;
    }

    private void isValid(ArticleDTO dto) {
        Optional<ArticleEntity> optional = articleRepository.findByTitle(dto.getTitle());
        if (optional.isPresent()) {
            throw new ItemAlreadyExistsException("This Article already exists!");
        }
    }

    public ArticleDTO update(ArticleDTO dto) {
        Optional<ArticleEntity> optional = articleRepository.findByTitle(dto.getTitle());
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found");
        }

        ArticleEntity entity = optional.get();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setAttachId(dto.getAttachId());
        entity.setRegion(regionService.getById(dto.getRegionId()));
        entity.setCategory(categoryService.getById(dto.getCategoryId()));
        articleRepository.save(entity);

        return toDTO(entity);
    }

    public int delete(String id) {
//        ArticleEntity entity = getById(id);
        return articleRepository.updateVisible(false, id);
    }

    private ArticleEntity getById(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found");
        }
        if (optional.get().getVisible() == false) {
            throw new ItemNotFoundException("Item not found");
        }
        return optional.get();
    }

    public Boolean changeStatus(String id, ArticleStatus status, Integer publisherId) {
        ArticleEntity entity = getById(id);
        if (entity.getStatus().equals(status)) {
            return false;
        }

        if (status.equals(ArticleStatus.PUBLISHED)) {
//            if (entity.getVisible()==false){
//                throw new MethodNotAllowedException("Visible is  false cannot publish");
//            }
//            entity.setVisible(true);
            entity.setPublisher(profileService.getById(publisherId));
            entity.setPublishedDate(LocalDateTime.now());
        }
        entity.setStatus(status);
        articleRepository.save(entity);
        return true;
    }

    public Object getTop5ByTypeId(Integer typeId) {
        List<ArticleShortInfo> entityList = articleRepository.getTop5(typeId, ArticleStatus.PUBLISHED.name());
        return toShortInfo(entityList);
    }

    public Object getTop3ByTypeId(Integer typeId) {
        List<ArticleShortInfo> entityList = articleRepository.getTop3(typeId, ArticleStatus.PUBLISHED.name());
        return toShortInfo(entityList);
    }

    private List<ArticleShortInfoDTO> toShortInfo(List<ArticleShortInfo> entityList) {
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setPublishedDate(entity.getPublished_date());
            dto.setImageId(entity.getImageId());
            dtoList.add(dto);
        });
        return dtoList;
    }
}
