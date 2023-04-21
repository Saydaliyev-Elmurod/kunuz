package com.example.kunuz.repository;

import com.example.kunuz.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> {
    Integer countByCreatedDateAfter( LocalDateTime emailLimitTime);
    Page<EmailHistoryEntity> getByEmail(String  email, Pageable pageable);
    Page<EmailHistoryEntity> getByCreatedDateBetween(LocalDateTime fDate,LocalDateTime toDate, Pageable pageable);
}
