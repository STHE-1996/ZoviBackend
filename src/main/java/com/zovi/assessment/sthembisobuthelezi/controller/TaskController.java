package com.zovi.assessment.sthembisobuthelezi.controller;

import com.zovi.assessment.sthembisobuthelezi.model.TaskModel;
import com.zovi.assessment.sthembisobuthelezi.requests.TaskRequest;
import com.zovi.assessment.sthembisobuthelezi.requests.UpdateStatusRequest;
import com.zovi.assessment.sthembisobuthelezi.requests.UpdateTaskRequest;
import com.zovi.assessment.sthembisobuthelezi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @PostMapping("/CreateTask")
    private TaskModel CreateTask(@RequestBody TaskRequest request) throws ChangeSetPersister.NotFoundException {
        return taskService.createTaskRecord(request);
    }

    @PostMapping("/UpdateTask")
    private TaskModel UpdateTask(@RequestBody UpdateTaskRequest request) throws ChangeSetPersister.NotFoundException {
        return taskService.updateTaskRecord(request);
    }


    @DeleteMapping("/deleteTask/{userId}/{taskId}")
    public ResponseEntity<String> deleteTaskRecord(@PathVariable String userId, @PathVariable String taskId) {
        try {
            taskService.deleteTaskRecord(userId, taskId);
            return ResponseEntity.ok("Task record deleted successfully.");
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/GetAllTask/{userId}")
    public ResponseEntity<?> getAllTaskRecords(@PathVariable String userId) {
        try {
            List<TaskModel> taskRecords = taskService.getAllTaskRecords(userId);
            return ResponseEntity.ok(taskRecords);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/updateStatus")
    public TaskModel updateTaskStatus(@RequestBody UpdateStatusRequest request) {
        return taskService.updateTaskStatus(request.getId(), request.getStatus());
    }


    @PostMapping("/{userId}/start-timer/{taskId}")
    public ResponseEntity<TaskModel> startTimer(@PathVariable String userId, @PathVariable String taskId) throws ChangeSetPersister.NotFoundException {
        TaskModel taskModel = taskService.startTaskTimer(userId, taskId);
        return ResponseEntity.ok(taskModel);
    }

    @PostMapping("/{userId}/stop-timer/{taskId}")
    public ResponseEntity<TaskModel> stopTimer(@PathVariable String userId, @PathVariable String taskId) throws ChangeSetPersister.NotFoundException {
        TaskModel taskModel = taskService.stopTaskTimer(userId, taskId);
        return ResponseEntity.ok(taskModel);
    }
}
