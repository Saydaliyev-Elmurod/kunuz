package com.example.kunuz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Table(name = "region")
@Entity
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_uz",unique = true)
    private String nameUz;
    @Column(name = "name_ru",unique = true)
    private String nameRu;
    @Column(name = "name_en",unique = true)
    private String nameEn;
    @Column(name = "visible")
    private Boolean visible;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
