package com.example.kunuz.service;

import com.example.kunuz.dto.ProfileDTO;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.GeneralStatus;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exps.AppBadRequestException;
import com.example.kunuz.exps.MethodNotAllowedException;
import com.example.kunuz.repository.ProfileRepository;
import com.example.kunuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        dto.setPassword(null);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(ProfileDTO dto) {
        if (dto.getPassword().length() < 6) {
            throw new AppBadRequestException("Password length is less than 6 ");
        } else if (!(dto.getEmail().endsWith("@mail.ru") || dto.getEmail().endsWith("@gmail.com"))) {
            throw new AppBadRequestException("Email incorrect");
        }
        if (!profileRepository.findByEmail(dto.getEmail()).isEmpty()) {
            throw new AppBadRequestException("This email is already registered");
        }
        if (!(dto.getRole().equals(ProfileRole.MODERATOR) || dto.getRole().equals(ProfileRole.PUBLISHER))) {
            throw new MethodNotAllowedException("You cannot create ");
        }
        return;
    }

}
