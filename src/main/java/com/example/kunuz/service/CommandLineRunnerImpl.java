package com.example.kunuz.service;

import com.example.kunuz.repository.ProfileRepository;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
//        String email = "adminjon_mazgi@gmail.com";
//        Optional<ProfileEntity> profileEntity = profileRepository.findByEmail(email);
//        if (profileEntity.isEmpty()) {
//            ProfileEntity entity = new ProfileEntity();
//            entity.setName("admin");
//            entity.setSurname("adminjon");
//            entity.setPhone("1234567");
//            entity.setEmail(email);
//            entity.setRole(ProfileRole.ADMIN);
//            entity.setPassword(MD5Util.getMd5Hash("12345"));
//            entity.setStatus(GeneralStatus.ACTIVE);
//            profileRepository.save(entity);
//            System.out.println("Amdin created");
//        }
    }
}
