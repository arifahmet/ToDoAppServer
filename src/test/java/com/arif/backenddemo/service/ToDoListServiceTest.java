package com.arif.backenddemo.service;

import com.arif.backenddemo.domain.ToDoList;
import com.arif.backenddemo.domain.User;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.repository.ToDoItemRepository;
import com.arif.backenddemo.repository.ToDoListRepository;
import com.arif.backenddemo.service.impl.ToDoItemServiceImpl;
import com.arif.backenddemo.service.impl.ToDoListServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ToDoListServiceTest {

    @Configuration
    static class ContextConfiguration {
        @Bean
        public ToDoListService toDoListService() {
            ToDoListService toDoListService = new ToDoListServiceImpl();
            return toDoListService;
        }
    }

    @Autowired
    private ToDoListService toDoListService;

    @MockBean
    private ToDoItemService toDoItemService;

    @MockBean
    private DependencyService dependencyService;

    @MockBean
    private ToDoListRepository toDoListRepository;

    @Test
    public void create_ShouldPersistUser() {

        ToDoList toDoList = new ToDoList();
        toDoList.setName("test");

        when(toDoListRepository.save(isA(ToDoList.class))).thenReturn(toDoList);

        ToDoList returnedToDoList = toDoListService.create(toDoList);

        verify(toDoListRepository, times(1)).save(isA(ToDoList.class));
        assertNotNull(returnedToDoList);
    }

    @Test
    public void getAllByUser_ShouldReturnToDoLists() {
        User user = new User("user","user","password","USER");
        ToDoList toDoList1 = new ToDoList();
        toDoList1.setId(1111L);
        toDoList1.setName("test1");
        toDoList1.setUser(user);

        ToDoList toDoList2 = new ToDoList();
        toDoList1.setId(22222L);
        toDoList2.setName("test2");
        toDoList1.setUser(user);

        List<ToDoList> response = new ArrayList<>();
        response.add(toDoList1);
        response.add(toDoList2);

        when(toDoListRepository.findByUser(isA(User.class))).thenReturn(response);

        List<ToDoList> returnedList = toDoListService.getAllByUser(user);

        verify(toDoListRepository, times(1)).findByUser(isA(User.class));

        assertEquals(returnedList.size(), 2);
    }

    @Test
    public void findToDoListById_ShouldReturnToDoList() {
        User user = new User("user","user","password","USER");
        ToDoList toDoList = new ToDoList();
        toDoList.setId(1111L);
        toDoList.setName("test1");
        toDoList.setUser(user);

        when(toDoListRepository.findById(isA(Long.class))).thenReturn(java.util.Optional.ofNullable(toDoList));

        ToDoList response = toDoListService.findToDoListById(11111L);

        verify(toDoListRepository, times(1)).findById(isA(Long.class));

        assertEquals(response,toDoList);

    }

    @Test
    public void deleteTodoList_ShouldReturnApiResponse_WithTrueStatus_WhenTrueUserOwnerOfToDoLisit() {
        User user = new User("user","user","password","USER");
        ToDoList toDoList = new ToDoList();
        toDoList.setId(1111L);
        toDoList.setName("test1");
        toDoList.setUser(user);

        String requestUsername = "user";

        when(toDoListRepository.findById(isA(Long.class))).thenReturn(java.util.Optional.ofNullable(toDoList));

        ApiResponse apiResponse = toDoListService.deleteTodoList(11111L, requestUsername);

        verify(toDoListRepository, times(1)).findById(isA(Long.class));
        verify(toDoListRepository, times(1)).delete(isA(ToDoList.class));

        assertEquals(apiResponse.getStatus(), true);


    }

    @Test
    public void deleteTodoList_ShouldReturnApiResponse_WithFalseStatus_WhenComeWithNotUserOwnerOfToDoLisit() {
        User user = new User("user","user","password","USER");
        ToDoList toDoList = new ToDoList();
        toDoList.setId(1111L);
        toDoList.setName("test1");
        toDoList.setUser(user);

        String requestUsername = "notowner";

        when(toDoListRepository.findById(isA(Long.class))).thenReturn(java.util.Optional.ofNullable(toDoList));

        ApiResponse apiResponse = toDoListService.deleteTodoList(11111L, requestUsername);

        verify(toDoListRepository, times(1)).findById(isA(Long.class));
        verify(toDoListRepository, times(0)).delete(isA(ToDoList.class));

        assertEquals(apiResponse.getStatus(), false);


    }

    @Test
    public void deleteTodoList_ShouldReturnApiResponse_WithFalseStatus_WhenToDoListDidNotFind() {
        String requestUsername = "user";

        when(toDoListRepository.findById(isA(Long.class))).thenReturn(java.util.Optional.ofNullable(null));

        ApiResponse apiResponse = toDoListService.deleteTodoList(11111L, requestUsername);

        verify(toDoListRepository, times(1)).findById(isA(Long.class));
        verify(toDoListRepository, times(0)).delete(isA(ToDoList.class));

        assertEquals(apiResponse.getStatus(), false);


    }
}