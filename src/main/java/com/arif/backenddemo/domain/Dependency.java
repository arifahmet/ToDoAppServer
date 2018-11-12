package com.arif.backenddemo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"toDoItemId", "dependencyToDoItemId"})
})
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(name = "toDoItemId")
    private Long toDoItemId;
    @Column(name = "dependencyToDoItemId")
    private Long dependencyToDoItemId;

    public Dependency(){}

    public Dependency(Long toDoItemId, Long dependencyToDoItemId) {
        this.toDoItemId = toDoItemId;
        this.dependencyToDoItemId = dependencyToDoItemId;
    }
}
