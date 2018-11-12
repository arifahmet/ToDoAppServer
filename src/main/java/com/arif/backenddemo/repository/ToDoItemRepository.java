package com.arif.backenddemo.repository;

import com.arif.backenddemo.domain.ToDoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoItemRepository extends CrudRepository<ToDoItem, Long> {
    List<ToDoItem> findByToDoListId(Long toDoListId);
}
