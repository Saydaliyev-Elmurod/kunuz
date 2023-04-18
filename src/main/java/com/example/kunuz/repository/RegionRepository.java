package com.example.kunuz.repository;

import com.example.kunuz.entity.ArticleTypeEntity;
import com.example.kunuz.entity.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface RegionRepository extends CrudRepository<RegionEntity,Integer> {
    Page<RegionEntity> findAll(Pageable pageable);

}
