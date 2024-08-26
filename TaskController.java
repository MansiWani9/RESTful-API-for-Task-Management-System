package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(Principal principal, @RequestBody Task task) {
        Long userId = Long.parseLong(principal.getName());
        Task createdTask = taskService.createTask(userId, task);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(taskService.getTasksByUser(userId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(taskId, task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();

 // Filtering tasks based on status, priority, and due date
    @GetMapping("/filter")
    public String filterTasks(
            @RequestParam(name = "status", required = false) TaskStatus status,
            @RequestParam(name = "priority", required = false) TaskPriority priority,
            @RequestParam(name = "dueDate", required = false) String dueDateStr,
            Principal principal,
            Model model) {

        Long userId = Long.parseLong(principal.getName());
        Date dueDate = null;

        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            try {
                dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateStr);
            } catch (Exception e) {
                // handle parsing error
            }
        }

        List<Task> tasks = taskService.filterTasks(userId, status, priority, dueDate);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    // Searching tasks based on title or description
    @GetMapping("/search")
    public String searchTasks(@RequestParam(name = "query") String query, Principal principal, Model model) {
        Long userId = Long.parseLong(principal.getName());
        List<Task> tasks = taskService.searchTasks(userId, query);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }
}
   
