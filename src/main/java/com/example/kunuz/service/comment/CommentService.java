package com.example.kunuz.service.comment;

import com.example.kunuz.dto.article.ArticleDTO;
import com.example.kunuz.dto.comment.CommentDTO;
import com.example.kunuz.dto.comment.CommentFilterDTO;
import com.example.kunuz.dto.profile.ProfileDTO;
import com.example.kunuz.entity.CommentEntity;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.exps.MethodNotAllowedException;
import com.example.kunuz.repository.CommentFilterRepository;
import com.example.kunuz.repository.CommentRepository;
import com.example.kunuz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentFilterRepository commentFilterRepository;

    public CommentDTO create(CommentDTO dto, Integer creater_id) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(creater_id);
        entity.setReplyId(dto.getReplyId());
        commentRepository.save(entity);
        dto.setId(commentRepository.save(entity).getId());
        return dto;
    }

    public CommentDTO update(CommentDTO dto, Integer commentId, Integer user_id) {
        CommentEntity entity = getById(commentId);
        if (!entity.getProfileId().equals(user_id)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        entity.setContent(dto.getContent());
        entity.setUpdateDate(LocalDateTime.now());
        commentRepository.save(entity);
        return toDTO(entity);
    }

    private CommentEntity getById(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found");
        }
        if (optional.get().getVisible() == false) {
            throw new ItemNotFoundException("Item not found");
        }
        return optional.get();
    }

    private CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setReplyId(entity.getReplyId());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setArticleId(entity.getArticleId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private List<CommentDTO> toList(List<CommentEntity> list) {
        List<CommentDTO> dtoList = new ArrayList<>();
        list.forEach(commentEntity -> dtoList.add(toDTO(commentEntity)));
        return dtoList;
    }

    public Integer delete(Integer id) {
        Integer prId = SpringSecurityUtil.getProfileId();
        if (getById(id).getProfileId()!=prId){
            throw new MethodNotAllowedException("Method not allowed");
        }
        int res = commentRepository.updateVisible(false, id);
        commentRepository.deleteReplyIdComment(id);
        return res;
    }

    public List<CommentDTO> list(String articleId) {
        return toList(commentRepository.getByVisibleAndArticleId(true, articleId));
    }

    public List<CommentDTO> listByAdmin(String articleId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<CommentEntity> pageObj = commentRepository.getByArticleId(articleId, pageable);
        List<CommentEntity> commentList = pageObj.getContent();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentList.forEach(commentEntity -> {
            commentDTOList.add(toFullDto(commentEntity));
        });
        return commentDTOList;
    }

    private CommentDTO toFullDto(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdateDate(entity.getUpdateDate());

        ProfileEntity profileEntity = entity.getProfile();
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profileEntity.getId());
        profileDTO.setName(profileEntity.getName());
        profileDTO.setSurname(profileEntity.getSurname());

        dto.setProfileDTO(profileDTO);
        dto.setContent(entity.getContent());

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(entity.getArticleId());
        articleDTO.setTitle(entity.getArticle().getTitle());

        dto.setArticleDTO(articleDTO);

        dto.setReplyId(entity.getReplyId());
        return dto;

    }

    public Page<CommentDTO> filter(CommentFilterDTO dto, Integer page, Integer size) {
        Page<CommentEntity> pageObj = commentFilterRepository.filter(dto, page, size);
        return new PageImpl<>(toList(pageObj.getContent()), pageObj.getPageable(), pageObj.getTotalElements());
    }

    public Object replyCommentList(Integer commentId) {
        List<CommentEntity> entityList = commentRepository.replyCommentList(commentId);
        return toList(entityList);
    }
}
