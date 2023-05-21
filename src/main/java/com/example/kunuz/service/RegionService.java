package com.example.kunuz.service;

import com.example.kunuz.dto.RegionDTO;
import com.example.kunuz.entity.RegionEntity;
import com.example.kunuz.enums.LangEnum;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegionService {
    private  final RegionRepository regionRepository;
    public RegionDTO create(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setNameUz(dto.getNameUz());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        regionRepository.save(entity);
        return toDTO(entity);
    }

    private RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public RegionDTO updateById(RegionDTO dto) {
        Optional<RegionEntity> optional = regionRepository.findById(dto.getId());
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Item not found");
        }
        RegionEntity entity = optional.get();
        filter(entity, dto);
        regionRepository.save(entity);
        return toDTO(entity);
    }
    private RegionEntity filter(RegionEntity entity, RegionDTO dto) {
        if (dto.getNameUz() != null) {
            entity.setNameUz(dto.getNameUz());
        }
        if (dto.getNameRu() != null) {
            entity.setNameRu(dto.getNameRu());
        }
        if (dto.getNameEn() != null) {
            entity.setNameEn(dto.getNameEn());
        }
        if (dto.getVisible() != null) {
            entity.setVisible(dto.getVisible());
        }
        return entity;
    }
    public RegionEntity getById(Integer id) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()|| !optional.get().getVisible()){
            throw new ItemNotFoundException("Item not found");
        }
        return optional.get();
    }
    public void deleteById(Integer id) {
        regionRepository.updateVisible(id);

    }
    public Page<RegionDTO> getList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<RegionEntity> entityList = regionRepository.findAllByVisibility(true,pageable);
        return new PageImpl<>(toList(entityList.getContent()), pageable, entityList.getTotalElements());
    }

    public HashMap<Integer, String> getList(LangEnum name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<RegionEntity> entityList = regionRepository.findAllByVisibility(true,pageable);
        HashMap<Integer, String> map = new HashMap<>();
        entityList.getContent().stream().map(entity -> {
            switch (name) {
                case en -> map.put(entity.getId(), entity.getNameEn());
                case ru -> map.put(entity.getId(), entity.getNameRu());
                case uz -> map.put(entity.getId(), entity.getNameUz());
            }
            return map;
        }).collect(Collectors.toList());
        return map;
    }
    private List<RegionDTO> toList(List<RegionEntity> entityList) {
        List<RegionDTO> dtoList = new ArrayList<>();
        entityList.forEach(articleTypeEntity -> dtoList.add(toDTO(articleTypeEntity)));
        return dtoList;
    }
}
