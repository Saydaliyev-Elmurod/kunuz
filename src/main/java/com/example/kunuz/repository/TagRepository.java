package com.example.kunuz.repository;

import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    TagEntity getByName(String name);
}
