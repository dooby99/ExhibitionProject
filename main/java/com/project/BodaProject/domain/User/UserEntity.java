package com.project.BodaProject.domain.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Slf4j
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pw", nullable = false)
    private String password;

    @Column(name = "name", unique = true)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime createT;

    @Builder
    public UserEntity(Long id,String password, String name, String email, LocalDateTime createT){
        this.id= id;
        this.password= password;
        this.name=name;
        this.email=email;
        this.createT=createT;
    }

}
