package com.example.kunuz.service.article;

import com.example.kunuz.dto.*;
import com.example.kunuz.dto.article.*;
import com.example.kunuz.enums.LangEnum;
import com.example.kunuz.mapper.ArticleShortInfo;
import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.repository.article.ArticleFilterRepository;
import com.example.kunuz.repository.article.ArticleRepository;
import com.example.kunuz.service.AttachService;
import com.example.kunuz.service.TagService;
import com.example.kunuz.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final TagService tagService;
    private final AttachService attachService;
    private final ArticleFilterRepository articleFilterRepository;

    public ArticleDTO create(ArticleDTO dto) {
        Integer moderator_id = SpringSecurityUtil.getProfileId();
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

    private ArticleFullInfoDTO toFullInfoDTO(ArticleEntity entity, LangEnum langEnum) {
        ArticleFullInfoDTO dto = new ArticleFullInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setViewCount(entity.getViewCount());
        dto.setAttach(attachService.getAttachLink(entity.getAttachId()));

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(entity.getCategoryId());

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(entity.getRegionId());

        ArticleTypeDTO articleTypeDTO = new ArticleTypeDTO();
        articleTypeDTO.setId(entity.getTypeId());
        switch (langEnum) {
            case en -> {
                regionDTO.setNameEn(entity.getRegion().getNameEn());
                categoryDTO.setNameEn(entity.getCategory().getNameEn());
                articleTypeDTO.setNameEn(entity.getType().getNameEn());
            }
            case ru -> {
                regionDTO.setNameRu(entity.getRegion().getNameRu());
                categoryDTO.setNameRu(entity.getCategory().getNameRu());
                articleTypeDTO.setNameRu(entity.getType().getNameRu());

            }
            case uz -> {
                regionDTO.setNameUz(entity.getRegion().getNameUz());
                categoryDTO.setNameUz(entity.getCategory().getNameUz());
                articleTypeDTO.setNameUz(entity.getType().getNameUz());

            }

        }
        dto.setRegion(regionDTO);
        dto.setCategory(categoryDTO);
        dto.setType(articleTypeDTO);
        return dto;
    }

    public int delete(String id) {
        return articleRepository.updateVisible(false, id);
    }

    private ArticleEntity getById(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found");
        }
        if (!optional.get().getVisible()) {
            throw new ItemNotFoundException("Item not found");
        }
        return optional.get();
    }

    public Boolean changeStatusToPublish(String id, ArticleStatus status, Integer publisherId) {
        ArticleEntity entity = getById(id);
        if (status.equals(ArticleStatus.PUBLISHED)) {
            entity.setPublishedDate(LocalDateTime.now());
            entity.setPublisherId(publisherId);
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

    public Object getTop8ByTypeId(List<String> idList) {
        List<ArticleEntity> entityList = articleRepository.find8ByTypeIdExceptIdLists(ArticleStatus.PUBLISHED, idList);
        return toShortInfoList(entityList);
    }

    private List<ArticleShortInfoDTO> toShortInfo(List<ArticleShortInfo> entityList) {
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setPublishedDate(entity.getPublished_date());
            dto.setImage(attachService.getAttachLink(entity.getAttachId()));
            dtoList.add(dto);
        });
        return dtoList;
    }

    private List<ArticleShortInfoDTO> toShortInfoList(List<ArticleEntity> entityList) {
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setPublishedDate(entity.getPublishedDate());
            dto.setImage(attachService.getAttachLink(entity.getAttachId()));
            dtoList.add(dto);
        });
        return dtoList;
    }

    public ArticleFullInfoDTO getByIdAndLang(String articleId, LangEnum lang) {
        ArticleEntity entity = getById(articleId);
        return toFullInfoDTO(entity, lang);
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
        List<ArticleShortInfo> entityList = articleRepository.getTopNTypeAndRegion(typeId, regionId, 5);
        return toShortInfo(entityList);
    }

    public Page<ArticleShortInfoDTO> getArticleByRegion(Integer regionId, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "view_count");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleShortInfo> entityList = articleRepository.getArticleByRegion(regionId, pageable);
        return new PageImpl<>(toShortInfo(entityList.getContent()), pageable, entityList.getTotalElements());
    }

    public Page<ArticleShortInfoDTO> getArticleByCategory(Integer categoryId, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "view_count");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleShortInfo> entityList = articleRepository.getArticleByCategory(categoryId, pageable);
        return new PageImpl<>(toShortInfo(entityList.getContent()), pageable, entityList.getTotalElements());
    }

    public Object filter(ArticleFilterDTO dto, int page, int size) {
        Page<ArticleEntity> pageObj = articleFilterRepository.filter(dto, page, size);
        return new PageImpl<>(toShortInfoList(pageObj.getContent()), PageRequest.of(page, size), pageObj.getTotalElements());
    }

    public List<ArticleShortInfoDTO> getByTop4ByTagName(String tag) {
        TagDTO tagDTO = tagService.getByName(tag);
        List<ArticleShortInfo> entityList = articleRepository.getByTagNameNative(tagDTO.getId());
        return toShortInfo(entityList);
    }

    public int increaseViewCount(String articleId) {
        articleRepository.increaseViewCount(articleId);
        return articleRepository.getViewCount(articleId);
    }

    public int increaseShareCount(String articleId) {
        articleRepository.increaseShareCount(articleId);
        return articleRepository.getShareCount(articleId);
    }
}
