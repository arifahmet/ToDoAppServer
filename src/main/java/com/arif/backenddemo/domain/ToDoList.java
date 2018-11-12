package com.arif.backenddemo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"name", "userId"})
})
public class ToDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public ToDoList(){}

    public ToDoList(String name, User user) {
        this.name = name;
    }
}
