package com.arif.backenddemo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"name", "toDoListId"})
})
public class ToDoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Date deadline;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "toDoListId")
    private ToDoList toDoList;

    @ManyToMany
    @JoinTable(name="Dependency",
            joinColumns={@JoinColumn(name="toDoItemId")},
            inverseJoinColumns={@JoinColumn(name="dependencyToDoItemId")})
    private List<ToDoItem> dependency;

    public ToDoItem(){}

    public ToDoItem(String name, Date deadline, Boolean status, ToDoList toDoList) {
        this.name = name;
        this.deadline = deadline;
        this.status = status;
        this.toDoList = toDoList;
    }
}
