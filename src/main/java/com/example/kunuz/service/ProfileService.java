package com.example.kunuz.service;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.ProfileDTO;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.GeneralStatus;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exps.AppBadRequestException;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.exps.MethodNotAllowedException;
import com.example.kunuz.repository.ProfileRepository;
import com.example.kunuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto, Integer adminId) {
        // check - homework
        isValidProfile(dto);

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword())); // MD5 ?
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        entity.setStatus(GeneralStatus.ACTIVE);
        entity.setPrtId(adminId);
        profileRepository.save(entity); // save profile
        dto.setStatus(entity.getStatus());
        dto.setPassword(null);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(ProfileDTO dto) {
        if (dto.getPassword().length() < 6) {
            throw new AppBadRequestException("Password length is less than 6 ");
        } else if (!(dto.getEmail().contains("@") )) {
            throw new AppBadRequestException("Email incorrect");
        }
        if (!profileRepository.findByEmail(dto.getEmail()).isEmpty()) {
            throw new AppBadRequestException("This email is already registered");
        }
        if (!(dto.getRole().equals(ProfileRole.MODERATOR) || dto.getRole().equals(ProfileRole.PUBLISHER))) {
            throw new MethodNotAllowedException("You cannot create ");
        }

    }

    public ProfileDTO updateByAdmin(ProfileDTO dto, JwtDTO jwtDTO) {
        ProfileEntity entity = getById(dto.getId());
        entity = filterAdmin(entity, dto);
        profileRepository.save(entity);
        return toDTO(entity);
    }

    public ProfileEntity getById(Integer id) {
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(() ->
        {
            throw new ItemNotFoundException("Item not found");
        });
        return entity;
    }

    private ProfileEntity filterAdmin(ProfileEntity entity, ProfileDTO dto) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getSurname() != null) {
            entity.setSurname(dto.getSurname());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getPassword() != null) {
            entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        return entity;
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPassword(null);
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private ProfileEntity filter(ProfileEntity entity, ProfileDTO dto) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getSurname() != null) {
            entity.setSurname(dto.getSurname());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getPassword() != null) {
            entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        }

        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        return entity;
    }

    public ProfileDTO update(ProfileDTO dto) {
        ProfileEntity entity = getById(dto.getId());
        filter(entity, dto);
        profileRepository.save(entity);
        return toDTO(entity);
    }

    public Page<ProfileDTO> getAll(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProfileEntity> entityPage = profileRepository.findAll(pageable);
        return new PageImpl<>(toList(entityPage.getContent()), pageable, entityPage.getTotalElements());
    }

    public List<ProfileDTO> toList(List<ProfileEntity> entityList) {
        List<ProfileDTO> dtoList = new ArrayList<>();
        entityList.forEach(profileEntity -> dtoList.add(toDTO(profileEntity)));
        return dtoList;
    }

    public void deleteById(Integer id) {
        ProfileEntity entity = getById(id);
        profileRepository.delete(entity);
    }
}
