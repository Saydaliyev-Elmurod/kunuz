package com.example.kunuz.service;

import com.example.kunuz.dto.ArticleFullInfoDTO;
import com.example.kunuz.dto.ArticleShortInfoDTO;
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
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setModeratorId(moderator_id);
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setTypeId(dto.getTypeId());
        entity.setId(UUID.randomUUID().toString());
        entity = articleRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public ArticleDTO update(ArticleDTO dto, String articleId) {
        ArticleEntity entity = getById(articleId);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);
        dto.setId(articleId);
        return dto;
    }


    private ArticleDTO toDTO(ArticleEntity entity) {

        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
//        dto.setSharedCount(entity.getSharedCount());
        dto.setAttachId(entity.getAttachId());
        dto.setRegionId(entity.getRegion().getId());
        dto.setCategoryId(entity.getCategory().getId());
//        dto.setModeratorId(entity.getModerator().getId());
//        dto.setPublisherId(entity.getPublisher().getId());
//        dto.setStatus(entity.getStatus());
//        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setPublishedDate(entity.getPublishedDate());
//        dto.setVisible(entity.getVisible());
//        dto.setViewCount(entity.getViewCount());
        return dto;
    }

    private ArticleFullInfoDTO toFullInfoDTO(ArticleEntity entity) {

        ArticleFullInfoDTO dto = new ArticleFullInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setViewCount(entity.getViewCount());
        dto.setAttachId(entity.getAttachId());
        dto.setRegionId(entity.getRegionId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setTypeId(entity.getTypeId());
        return dto;
    }
    /*ArticleFullInfo
    id(uuid),title,description,content,shared_count,
    region(key,name),category(key,name),published_date,view_count,like_count,
    tagList(name)*/


    public int delete(String id) {
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

    public Boolean changeStatusToPublish(String id, ArticleStatus status, Integer publisherId) {
        ArticleEntity entity = getById(id);
        if (entity.getStatus().equals(status)) {
            return false;
        }
        if (status.equals(ArticleStatus.PUBLISHED)) {
            entity.setPublisherId(publisherId);
            entity.setPublishedDate(LocalDateTime.now());
        }
        entity.setStatus(status);
        articleRepository.save(entity);
        return true;
    }

    public Object getTop5ByTypeId(Integer typeId) {
        List<ArticleShortInfo> entityList = articleRepository.getTopN(typeId, ArticleStatus.PUBLISHED.name(), 5);
        return toShortInfo(entityList);
    }

    public Object getTop3ByTypeId(Integer typeId) {
        List<ArticleShortInfo> entityList = articleRepository.getTopN(typeId, ArticleStatus.PUBLISHED.name(), 3);
        return toShortInfo(entityList);
    }

    public Object getTop8ByTypeId(Integer typeId) {
        List<ArticleShortInfo> entityList = articleRepository.getTopN(typeId, ArticleStatus.PUBLISHED.name(), 8);
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
            dto.setAttachId(entity.getAttachId());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public ArticleFullInfoDTO getByIdAndLang(String articleId) {
        ArticleEntity entity = getById(articleId);
        return toFullInfoDTO(entity);
    }

    public Object getByTypeWithoutId(String articleId, Integer typeId) {
        List<ArticleShortInfo> entityList = articleRepository.getTopNWithoutId(typeId, articleId, 4);
        return toShortInfo(entityList);
    }

    public Object getByTop4Read() {
        List<ArticleShortInfo> entityList = articleRepository.getTopNRead(4);
        return toShortInfo(entityList);
    }

    public Object getTop5TypeAndRegion(Integer regionId, Integer typeId) {
        List<ArticleShortInfo> entityList = articleRepository.getTopNTypeAndRegion(typeId,regionId,5);
        return toShortInfo(entityList);
    }
}
