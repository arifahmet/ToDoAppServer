package com.arif.backenddemo.repository;

import com.arif.backenddemo.domain.ToDoList;
import com.arif.backenddemo.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepository extends CrudRepository<ToDoList, Long> {
    List<ToDoList> findByUser(User user);
}
