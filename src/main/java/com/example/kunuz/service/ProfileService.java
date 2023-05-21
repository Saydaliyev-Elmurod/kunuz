package com.example.kunuz.service;

import com.example.kunuz.dto.AttachDTO;
import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.profile.ProfileDTO;
import com.example.kunuz.dto.profile.ProfileFilterDTO;
import com.example.kunuz.entity.AttachEntity;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.GeneralStatus;
import com.example.kunuz.exps.AppBadRequestException;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.repository.ProfileFilterRepository;
import com.example.kunuz.repository.ProfileRepository;
import com.example.kunuz.util.MD5Util;
import com.example.kunuz.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final AttachService attachService;
    private final ProfileFilterRepository profileFilterRepository;

//    private final

    public ProfileDTO create(ProfileDTO dto, Integer adminId) {
        // check
        isValidProfile(dto);

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword())); // MD5
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        entity.setStatus(GeneralStatus.ROLE_ACTIVE);
        entity.setPrtId(SpringSecurityUtil.getProfileId());
        profileRepository.save(entity); // save profile
        dto.setStatus(entity.getStatus());
        dto.setPassword(null);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(ProfileDTO dto) {
        if (profileRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new AppBadRequestException("This email is already registered");
        }
    }

    public ProfileDTO updateByAdmin(ProfileDTO dto) {
        ProfileEntity entity = getById(dto.getId());
        profileRepository.save(filterAdmin(entity, dto));
        return toDTO(entity);
    }

    public ProfileEntity getById(Integer id) {
        return profileRepository.findById(id).orElseThrow(() ->
        {
            throw new ItemNotFoundException("Item not found");
        });
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
        profileRepository.deleteById(id);
    }

    public AttachDTO uploadImage(MultipartFile file, Integer jwtDTOId) {
        ProfileEntity profileEntity = getById(jwtDTOId);
        AttachDTO newPhoto = attachService.save(file);
        AttachEntity oldPhoto = profileEntity.getAttachEntity();
        profileEntity.setAttachEntity(attachService.get(newPhoto.getId()));
        profileRepository.save(profileEntity);
        //delete old image
        if (oldPhoto != null) {
            attachService.delete(oldPhoto.getId());
        }
        return newPhoto;
    }

    public List<ProfileDTO> filter(ProfileFilterDTO dto) {
        List<ProfileEntity> entityList = profileFilterRepository.filter(dto);
        return toList(entityList);
    }
}
