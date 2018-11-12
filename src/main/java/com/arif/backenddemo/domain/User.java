package com.arif.backenddemo.domain;


import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String username;
    private String password;
    private String authorities;

    public User(){}
    public User(String name,String username, String password, String authorities) {
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
        this.setAuthorities(authorities);
    }
}
