package com.example.taskmanager;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@SpringJUnitConfig
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task;

    @BeforeEach
    public void setup() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Sample Task");
        task.setDescription("This is a sample task");
        task.setStatus("Todo");
        task.setPriority("High");
        task.setDueDate(LocalDate.of(2024, 8, 26));
    }

    @Test
    public void testCreateTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sample Task"))
                .andExpect(jsonPath("$.description").value("This is a sample task"))
                .andExpect(jsonPath("$.status").value("Todo"))
                .andExpect(jsonPath("$.priority").value("High"));
    }

    @Test
    public void testGetTaskById() throws Exception {
        when(taskService.getTaskById(anyLong())).thenReturn(task);

        mockMvc.perform(get("/api/tasks/{taskId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sample Task"))
                .andExpect(jsonPath("$.description").value("This is a sample task"))
                .andExpect(jsonPath("$.status").value("Todo"))
                .andExpect(jsonPath("$.priority").value("High"));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Sample Task"))
                .andExpect(jsonPath("$[0].description").value("This is a sample task"))
                .andExpect(jsonPath("$[0].status").value("Todo"))
                .andExpect(jsonPath("$[0].priority").value("High"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/{taskId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sample Task"))
                .andExpect(jsonPath("$.description").value("This is a sample task"))
                .andExpect(jsonPath("$.status").value("Todo"))
                .andExpect(jsonPath("$.priority").value("High"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(anyLong());

        mockMvc.perform(delete("/api/tasks/{taskId}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSearchTasks() throws Exception {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.searchTasks(anyString())).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks/search")
                .param("keyword", "Sample"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Sample Task"))
                .andExpect(jsonPath("$[0].description").value("This is a sample task"))
                .andExpect(jsonPath("$[0].status").value("Todo"))
                .andExpect(jsonPath("$[0].priority").value("High"));
    }
}

