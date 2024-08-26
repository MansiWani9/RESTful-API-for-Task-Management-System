package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private TaskController taskController;

    private final Long userId = 1L;
    private final Long taskId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetTasks() {
        List<Task> taskList = new ArrayList<>();
        Page<Task> taskPage = new PageImpl<>(taskList, PageRequest.of(0, 10), taskList.size());

        when(principal.getName()).thenReturn("testuser");
        when(userService.getUserIdByUsername("testuser")).thenReturn(userId);
        when(taskService.getTasksByUserId(userId, 0, 10)).thenReturn(taskPage);

        String viewName = taskController.getTasks(0, 10, null, null, null, null, principal, model);

        verify(model, times(1)).addAttribute("taskPage", taskPage);
        verify(model, times(1)).addAttribute("currentPage", 0);
        verify(model, times(1)).addAttribute("totalPages", taskPage.getTotalPages());
        verify(model, times(1)).addAttribute("totalItems", taskPage.getTotalElements());
        assertEquals("tasks", viewName);
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setTitle("New Task");

        when(principal.getName()).thenReturn("testuser");
        when(userService.getUserIdByUsername("testuser")).thenReturn(userId);

        String viewName = taskController.createTask(task, principal);

        verify(taskService, times(1)).saveTask(task);
        assertEquals("redirect:/tasks", viewName);
    }

    @Test
    void testGetTaskById_Found() {
        Task task = new Task();
        task.setId(taskId);

        when(principal.getName()).thenReturn("testuser");
        when(userService.getUserIdByUsername("testuser")).thenReturn(userId);
        when(taskService.getTaskByIdAndUserId(taskId, userId)).thenReturn(Optional.of(task));

        String viewName = taskController.getTaskById(taskId, model, principal);

        verify(model, times(1)).addAttribute("task", task);
        assertEquals("task_detail", viewName);
    }

    @Test
    void testGetTaskById_NotFound() {
        when(principal.getName()).thenReturn("testuser");
        when(userService.getUserIdByUsername("testuser")).thenReturn(userId);
        when(taskService.getTaskByIdAndUserId(taskId, userId)).thenReturn(Optional.empty());

        String viewName = taskController.getTaskById(taskId, model, principal);

        assertEquals("error/404", viewName);
    }

    @Test
    void testUpdateTask() {
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Updated Task");

        when(principal.getName()).thenReturn("testuser");
        when(userService.getUserIdByUsername("testuser")).thenReturn(userId);
        when(taskService.getTaskByIdAndUserId(taskId, userId)).thenReturn(Optional.of(task));

        String viewName = taskController.updateTask(taskId, task, principal);

        verify(taskService, times(1)).saveTask(task);
        assertEquals("redirect:/tasks", viewName);
    }

    @Test
    void testDeleteTask() {
        Task task = new Task();
        task.setId(taskId);

        when(principal.getName()).thenReturn("testuser");
        when(userService.getUserIdByUsername("testuser")).thenReturn(userId);
        when(taskService.getTaskByIdAndUserId(taskId, userId)).thenReturn(Optional.of(task));

        String viewName = taskController.deleteTask(taskId, principal);

        verify(taskService, times(1)).deleteTask(taskId);
        assertEquals("redirect:/tasks", viewName);
    }
}
