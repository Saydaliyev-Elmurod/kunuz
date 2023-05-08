package com.example.kunuz.repository;

import com.example.kunuz.dto.profile.ProfileFilterDTO;
import com.example.kunuz.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileFilterRepository {
    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> filter(ProfileFilterDTO filterDTO) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        builder.append("Select s From ProfileEntity as s where visible = true ");
        if (filterDTO.getName() != null) {
            builder.append(" and s.name = :name");
            params.put("name", filterDTO.getName());
        }
        if (filterDTO.getSurname() != null) {
            builder.append(" and s.surname = :surname");
            params.put("surname", filterDTO.getSurname());
        }
        if (filterDTO.getEmail() != null) {
            builder.append(" and s.email = :email");
            params.put("email", filterDTO.getEmail());
        }
        if (filterDTO.getPhone() != null) {
            builder.append(" and s.phone = :phone");
            params.put("phone", filterDTO.getPhone());
        }
        if (filterDTO.getRole() != null) {
            builder.append(" and s.role = :role");
            params.put("role", filterDTO.getRole());
        }
        if (filterDTO.getStatus() != null) {
            builder.append(" and s.status = :status");
            params.put("status", filterDTO.getStatus());
        }
        if (filterDTO.getCreatedDateFrom() != null && filterDTO.getCreatedDateTo() != null) {
            builder.append(" and s.createdDate between :dateFrom and :dateTo ");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
            params.put("dateTo", LocalDateTime.of(filterDTO.getCreatedDateTo(), LocalTime.MAX));
        } else if (filterDTO.getCreatedDateFrom() != null) {
            builder.append(" and s.createdDate >= :dateFrom ");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
        } else if (filterDTO.getCreatedDateTo() != null) {
            builder.append(" and s.createdDate <= :dateTo ");
            params.put("dateTo", LocalDateTime.of(filterDTO.getCreatedDateTo(), LocalTime.MIN));
        }

        // ....
        Query query = this.entityManager.createQuery(builder.toString());
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        List<ProfileEntity> profileList = query.getResultList();
        return profileList;
    }
}
