package com.example.kunuz.service;

import com.example.kunuz.dto.EmailHistoryDTO;
import com.example.kunuz.entity.EmailHistoryEntity;
import com.example.kunuz.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public Page<EmailHistoryDTO> getByEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<EmailHistoryEntity> entityPage = emailHistoryRepository.getByEmail(email, pageable);
        return new PageImpl<>(toList(entityPage.getContent()), pageable, entityPage.getTotalElements());
    }

    private EmailHistoryDTO toDTO(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private List<EmailHistoryDTO> toList(List<EmailHistoryEntity> list) {
        ArrayList<EmailHistoryDTO> dtoList = new ArrayList<>();
        list.forEach(emailHistoryEntity -> dtoList.add(toDTO(emailHistoryEntity)));
        return dtoList;
    }

    public Page<EmailHistoryDTO> getByDate(LocalDate date, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<EmailHistoryEntity> entityPage = emailHistoryRepository.
                getByCreatedDateBetween(date.atStartOfDay(),date.atStartOfDay().plus(1,ChronoUnit.DAYS), pageable);
        return new PageImpl<>(toList(entityPage.getContent()), pageable, entityPage.getTotalElements());
    }

}
