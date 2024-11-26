package com.sparseshadow.je_20241112.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data  
@Entity  
@Table (name = "users")  
public class User implements Serializable {
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Integer id;

    @Column(name = "username", unique = true)  
    private String username;

    @Column(name = "nickname")  
    private String nickname;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "role")  
    private String role = "user";  
}
