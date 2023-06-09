package com.example.kunuz.repository;

import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.GeneralStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmailAndPasswordAndVisible(String login, String md5Hash, boolean b);
    Optional<ProfileEntity> findByEmailAndStatus(String email, GeneralStatus status);
    Optional<ProfileEntity> findByEmail(String email);

    Page<ProfileEntity> findAll(Pageable pageable);
}
