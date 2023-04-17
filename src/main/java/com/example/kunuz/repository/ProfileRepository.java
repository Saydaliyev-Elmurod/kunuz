package com.example.kunuz.repository;

import com.example.kunuz.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmailAndPasswordAndVisible(String login, String md5Hash, boolean b);

}
