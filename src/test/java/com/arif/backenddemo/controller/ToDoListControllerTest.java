package com.arif.backenddemo.controller;

import com.arif.backenddemo.domain.ToDoList;
import com.arif.backenddemo.domain.User;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.security.EntryPointUnauthorizedHandler;
import com.arif.backenddemo.security.TokenUtils;
import com.arif.backenddemo.service.SecurityService;
import com.arif.backenddemo.service.ToDoListService;
import com.arif.backenddemo.service.UserService;
import com.arif.backenddemo.service.impl.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.isA;

@RunWith(SpringRunner.class)
@WebMvcTest(ToDoListController.class)
public class ToDoListControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private ToDoListService toDoListService;

    @MockBean
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;

    @MockBean
    private TokenUtils tokenUtils;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private UserService userService;



    @Test
    @WithMockUser(username = "user")
    public void createToDoList_ShouldReturnStatus400_WhenValidationErrorOccurs() throws Exception {
        mvc.perform(post("/todolist/create")
                .content("{\"name\":\"\"}")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user")
    public void createToDoList_ShouldReturnToDoList() throws Exception {
        ToDoList toDoList = new ToDoList();
        toDoList.setId(11111L);
        toDoList.setName("test");
        User user = new User("user","user","password","USER");
        user.setId(11111L);
        toDoList.setUser(user);

        given(toDoListService.create(isA(ToDoList.class))).willReturn(toDoList);
        given(userService.getUserByUsername(isA(String.class))).willReturn(user);

        mvc.perform(post("/todolist/create")
                .content("{\"name\":\"test\"}")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name", is("test")));

        verify(toDoListService, times(1)).create(isA(ToDoList.class));
        verify(userService, times(1)).getUserByUsername(isA(String.class));
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(toDoListService);
    }

    @Test
    @WithMockUser(username = "user")
    public void getToDoList_ShouldReturnToDoLists() throws Exception{

        ToDoList toDoList = new ToDoList();
        toDoList.setName("test");
        User user = new User("user","user","password","USER");
        toDoList.setUser(user);
        List<ToDoList> toDoLists = Collections.singletonList(toDoList);

        given(toDoListService.getAllByUser(isA(User.class))).willReturn(toDoLists);
        given(userService.getUserByUsername(isA(String.class))).willReturn(user);

        mvc.perform(get("/todolist/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].name", is("test")));
        verify(toDoListService, times(1)).getAllByUser(isA(User.class));
        verify(userService, times(1)).getUserByUsername(isA(String.class));
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(toDoListService);
    }

    @Test
    @WithMockUser(username = "user")
    public void deleteToDoList_ShouldReturnStatus400_WhenServiceResponseStatusFalse()throws Exception{
        ApiResponse testResponse = new ApiResponse("Failed", false);

        given(toDoListService.deleteTodoList(isA(Long.class), isA(String.class))).willReturn(testResponse);

        mvc.perform(get("/todolist/delete").param("id", "1"))
                .andExpect(status().isBadRequest());

        verify(toDoListService, times(1)).deleteTodoList(isA(Long.class), isA(String.class));
        verifyNoMoreInteractions(toDoListService);
    }

    @Test
    @WithMockUser(username = "user")
    public void deleteToDoList_ShouldReturnStatus200_WhenServiceResponseStatusTrue() throws Exception{
        ApiResponse testResponse = new ApiResponse("Succeeded", true);

        given(toDoListService.deleteTodoList(isA(Long.class), isA(String.class))).willReturn(testResponse);

        mvc.perform(get("/todolist/delete").param("id", "1"))
                .andExpect(status().isOk());

        verify(toDoListService, times(1)).deleteTodoList(isA(Long.class), isA(String.class));
        verifyNoMoreInteractions(toDoListService);

    }
}