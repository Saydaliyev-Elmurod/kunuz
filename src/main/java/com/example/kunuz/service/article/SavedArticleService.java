package com.example.kunuz.service.article;

import com.example.kunuz.dto.article.ArticleShortInfoDTO;
import com.example.kunuz.dto.article.SavedArticleDTO;
import com.example.kunuz.entity.SavedArticleEntity;
import com.example.kunuz.exps.ItemAlreadyExistsException;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.exps.MethodNotAllowedException;
import com.example.kunuz.mapper.SavedArticleShortInfoDTO;
import com.example.kunuz.repository.SavedArticleRepository;
import com.example.kunuz.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedArticleService {
    @Autowired
    private SavedArticleRepository savedArticleRepository;
    @Autowired
    private AttachService attachService;

    public SavedArticleDTO create(SavedArticleDTO dto, Integer user_id) {
        SavedArticleEntity oldEntity = savedArticleRepository.getByUserIdAndArticleId(user_id, dto.getArticleId());
       if (oldEntity!=null){
           throw new ItemAlreadyExistsException("Item already exist");
       }
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(user_id);
        entity = savedArticleRepository.save(entity);
        dto.setProfileId(user_id);
        dto.setArticleId(entity.getArticleId());
        dto.setId(entity.getId());
        return dto;
    }

    public void delete(Integer id, Integer jwtDTOId) {
        SavedArticleEntity entity = getById(id);
        if (entity.getProfileId() != jwtDTOId) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        savedArticleRepository.deleteById(id);
    }

    private SavedArticleEntity getById(Integer id) {
        Optional<SavedArticleEntity> optional = savedArticleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found");
        }
        return optional.get();
    }

    public List<SavedArticleShortInfoDTO> getList(Integer user_id) {
        List<SavedArticleEntity> entityList = savedArticleRepository.getList(user_id);
        return toList(entityList);
    }

    private List<SavedArticleShortInfoDTO> toList(List<SavedArticleEntity> entityList) {

        List<SavedArticleShortInfoDTO> dtoList = new ArrayList<>();
        entityList.forEach(savedArticleEntity -> dtoList.add(toDTO(savedArticleEntity)));
        return dtoList;
    }

    private SavedArticleShortInfoDTO toDTO(SavedArticleEntity savedArticleEntity) {
        SavedArticleShortInfoDTO dto = new SavedArticleShortInfoDTO();
        dto.setId(savedArticleEntity.getId());

        ArticleShortInfoDTO articleShortInfoDTO = new ArticleShortInfoDTO();
        articleShortInfoDTO.setId(savedArticleEntity.getArticle().getId());
        articleShortInfoDTO.setTitle(savedArticleEntity.getArticle().getTitle());
        articleShortInfoDTO.setDescription(savedArticleEntity.getArticle().getDescription());
        articleShortInfoDTO.setPublishedDate(savedArticleEntity.getArticle().getPublishedDate());
        articleShortInfoDTO.setImage(attachService.getAttachLink(savedArticleEntity.getArticle().getAttachId()));
        dto.setArticle(articleShortInfoDTO);
        return dto;
    }
}
