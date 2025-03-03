package com.zovi.assessment.sthembisobuthelezi.service;

import com.zovi.assessment.sthembisobuthelezi.enums.TaskStatus;
import com.zovi.assessment.sthembisobuthelezi.model.TaskModel;
import com.zovi.assessment.sthembisobuthelezi.requests.TaskRequest;
import com.zovi.assessment.sthembisobuthelezi.requests.UpdateTaskRequest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface TaskService {
    TaskModel createTaskRecord(TaskRequest request) throws ChangeSetPersister.NotFoundException;

    TaskModel updateTaskRecord(UpdateTaskRequest request) throws ChangeSetPersister.NotFoundException, ChangeSetPersister.NotFoundException;

    void deleteTaskRecord(String userId, String taskId) throws ChangeSetPersister.NotFoundException;

    List<TaskModel> getAllTaskRecords(String userId) throws ChangeSetPersister.NotFoundException;

    TaskModel updateTaskStatus(String id, TaskStatus status);

    TaskModel startTaskTimer(String userId, String taskId) throws ChangeSetPersister.NotFoundException;
//
    TaskModel stopTaskTimer(String userId, String taskId) throws ChangeSetPersister.NotFoundException;
}
