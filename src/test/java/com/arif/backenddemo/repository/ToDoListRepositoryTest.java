package com.arif.backenddemo.repository;

import com.arif.backenddemo.domain.ToDoList;
import com.arif.backenddemo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ToDoListRepositoryTest {

    @Autowired
    ToDoListRepository toDoListRepository;
    @Autowired
    UserRepository userRepository;
    @Test
    public void findByUser() {
    }

    @Test
    public void save_ShouldPersistUser() {
        User user = new User("user","user","password","USER");
        ToDoList toDoList = new ToDoList();
        toDoList.setId(1111L);
        toDoList.setName("test1");
        toDoList.setUser(user);

        ToDoList returnedToDoList = toDoListRepository.save(toDoList);

        assertNotNull(returnedToDoList.getId());
    }

    @Test
    public void findByUser_ShouldReturnNull_WhenToDoListsNotExists() {
        User user = new User("user","user","password","USER");
        userRepository.save(user);

        List<ToDoList> list = toDoListRepository.findByUser(user);

        assertEquals(list.size(), 0);
    }


    @Test
    public void findByUser_ShouldReturnUsers_WhenToDoListsExists() {
        User user = new User("user","user","password","USER");
        userRepository.save(user);

        ToDoList toDoList = new ToDoList();
        toDoList.setId(1111L);
        toDoList.setName("test1");
        toDoList.setUser(user);
        toDoListRepository.save(toDoList);

        List<ToDoList> list = toDoListRepository.findByUser(user);

        assertEquals(list.size(), 1);
    }

}