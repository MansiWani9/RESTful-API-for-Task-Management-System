package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskPriority;
import com.example.taskmanager.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByUserIdAndStatus(Long userId, TaskStatus status);

    List<Task> findByUserIdAndPriority(Long userId, TaskPriority priority);

    List<Task> findByUserIdAndDueDate(Long userId, Date dueDate);

    List<Task> findByUserIdAndTitleContainingOrDescriptionContaining(Long userId, String title, String description);
    
    // Combined filter
    List<Task> findByUserIdAndStatusAndPriorityAndDueDate(Long userId, TaskStatus status, TaskPriority priority, Date dueDate);

    Page<Task> findByUserId(Long userId, Pageable pageable);

    Page<Task> findByUserIdAndTitleContainingOrDescriptionContaining(Long userId, String title, String description, Pageable pageable);
}

