package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // 1. Save or Update Task
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    // 2. Get Paginated Tasks by User ID
    public Page<Task> getTasksByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByUserId(userId, pageable);
    }

    // 3. Get Task by ID and User ID
    public Optional<Task> getTaskByIdAndUserId(Long taskId, Long userId) {
        return taskRepository.findById(taskId).filter(task -> task.getUserId().equals(userId));
    }

    // 4. Delete Task
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    // 5. Search Tasks with Pagination
    public Page<Task> searchTasks(Long userId, String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByUserIdAndTitleContainingOrDescriptionContaining(userId, query, query, pageable);
    }

    // 6. Filter Tasks with Pagination
    public Page<Task> filterTasks(Long userId, String status, String priority, String dueDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (status != null && !status.isEmpty()) {
            return taskRepository.findByUserIdAndStatus(userId, status, pageable);
        }

        if (priority != null && !priority.isEmpty()) {
            return taskRepository.findByUserIdAndPriority(userId, priority, pageable);
        }

        if (dueDate != null && !dueDate.isEmpty()) {
            return taskRepository.findByUserIdAndDueDate(userId, dueDate, pageable);
        }

        return taskRepository.findByUserId(userId, pageable);
    }
}

