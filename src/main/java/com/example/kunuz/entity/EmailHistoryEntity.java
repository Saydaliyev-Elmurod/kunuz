package com.example.kunuz.entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "email_history")
@Entity
public class EmailHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 1000)
    private String message;
    @Column
    private String email;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}


