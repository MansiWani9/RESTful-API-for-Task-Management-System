package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    // 1. Create a new task
    @PostMapping
    public String createTask(@ModelAttribute Task task, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        task.setUserId(userId);
        taskService.saveTask(task);
        return "redirect:/api/tasks";
    }

    // 2. Get paginated tasks
    @GetMapping
    public String getTasks(@RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "10") int size,
                           Principal principal,
                           Model model) {

        Long userId = Long.parseLong(principal.getName());
        Page<Task> taskPage = taskService.getTasksByUserId(userId, page, size);
        
        model.addAttribute("taskPage", taskPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", taskPage.getTotalPages());

        return "tasks";
    }

    // 3. Get a single task by ID
    @GetMapping("/{id}")
    public String getTaskById(@PathVariable Long id, Model model, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        Optional<Task> task = taskService.getTaskByIdAndUserId(id, userId);
        
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "task_detail";
        } else {
            return "error/404";
        }
    }

    // 4. Update a task
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateTask(@PathVariable Long id, @ModelAttribute Task updatedTask, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        Optional<Task> taskOptional = taskService.getTaskByIdAndUserId(id, userId);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            task.setPriority(updatedTask.getPriority());
            task.setDueDate(updatedTask.getDueDate());
            taskService.saveTask(task);
            return "redirect:/api/tasks";
        } else {
            return "error/404";
        }
    }

    // 5. Delete a task
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTask(@PathVariable Long id, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        Optional<Task> task = taskService.getTaskByIdAndUserId(id, userId);

        if (task.isPresent()) {
            taskService.deleteTask(id);
            return "redirect:/api/tasks";
        } else {
            return "error/404";
        }
    }

    // 6. Search tasks (with pagination)
    @GetMapping("/search")
    public String searchTasks(@RequestParam(name = "query") String query,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "10") int size,
                              Principal principal,
                              Model model) {

        Long userId = Long.parseLong(principal.getName());
        Page<Task> taskPage = taskService.searchTasks(userId, query, page, size);

        model.addAttribute("taskPage", taskPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", taskPage.getTotalPages());

        return "tasks";
    }

    // 7. Filter tasks (with pagination)
    @GetMapping("/filter")
    public String filterTasks(@RequestParam(name = "status", required = false) String status,
                              @RequestParam(name = "priority", required = false) String priority,
                              @RequestParam(name = "dueDate", required = false) String dueDate,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "10") int size,
                              Principal principal,
                              Model model) {

        Long userId = Long.parseLong(principal.getName());
        Page<Task> taskPage = taskService.filterTasks(userId, status, priority, dueDate, page, size);

        model.addAttribute("taskPage", taskPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", taskPage.getTotalPages());

        return "tasks";
    }
}
