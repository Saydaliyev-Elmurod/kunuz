package com.example.kunuz.service;

import com.example.kunuz.dto.CommentDTO;
import com.example.kunuz.entity.CommentEntity;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.exps.MethodNotAllowedException;
import com.example.kunuz.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

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
        if (!entity.getProfileId().equals(user_id)){
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
        int res= commentRepository.updateVisible(false, id);
        commentRepository.deleteReplyIdComment(id);
        return res;
    }

    public List<CommentDTO> list() {
        return toList(commentRepository.getByVisible(true));
    }

}