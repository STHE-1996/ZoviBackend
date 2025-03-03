package com.zovi.assessment.sthembisobuthelezi.service.implemenation;

import com.zovi.assessment.sthembisobuthelezi.enums.TaskStatus;
import com.zovi.assessment.sthembisobuthelezi.model.TaskModel;
import com.zovi.assessment.sthembisobuthelezi.repository.UserRepository;
import com.zovi.assessment.sthembisobuthelezi.repository.TaskRepository;
import com.zovi.assessment.sthembisobuthelezi.requests.TaskRequest;
import com.zovi.assessment.sthembisobuthelezi.service.CommonService;
import com.zovi.assessment.sthembisobuthelezi.model.UserModel;
import com.zovi.assessment.sthembisobuthelezi.requests.UpdateTaskRequest;

import com.zovi.assessment.sthembisobuthelezi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class TaskServiceImplementation implements TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommonService commonService;


    private static final Set<String> generatedNumbers = new HashSet<>();
    private static final Random random = new Random();
    @Override
    public TaskModel createTaskRecord(TaskRequest request) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(request.getUserId());

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            TaskModel taskModel = new TaskModel();
            String taskId =  generateUniqueNumber();
            taskModel.setId(taskId);
            taskModel.setTaskName(request.getTaskName());
            taskModel.setDescription(request.getDescription());
            taskModel.setDate(new Date().toString());
            taskModel.setStartDate(request.getStartDate().toString());
            taskModel.setUserRole(request.getUserRole());
            taskModel.setDueDate(request.getDueDate().toString());
            taskModel.setStatus(TaskStatus.PENDING);
            userModel.getListOfTask().add(taskModel);
            userRepository.save(userModel);
            return taskModel;
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }


    @Override
    public TaskModel updateTaskRecord(UpdateTaskRequest request) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(request.getUserId());

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            Optional<TaskModel> taskModelOptional = userModel.getListOfTask().stream()
                    .filter(w -> w.getId().equals(request.getTaskId()))
                    .findFirst();

            if (taskModelOptional.isPresent()) {
                TaskModel taskModel = taskModelOptional.get();
                taskModel.setTaskName(request.getTaskName());
                taskModel.setDueDate(request.getDueDate());
                taskModel.setDate(new Date().toString());
                taskModel.setUserRole(request.getUserRole());
                userRepository.save(userModel);
                return taskModel;
            } else {
                throw new ChangeSetPersister.NotFoundException();
            }
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    public static String generateUniqueNumber() {
        String number;
        do {
            number = String.valueOf(random.nextInt(90000) + 10000); // Generate a number between 10000 and 99999
        } while (generatedNumbers.contains(number));

        generatedNumbers.add(number);
        return number;
    }


    @Override
    public void deleteTaskRecord(String userId, String taskId) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            Optional<TaskModel> taskModelOptional = userModel.getListOfTask().stream()
                    .filter(w -> w.getId().equals(taskId))
                    .findFirst();

            if (taskModelOptional.isPresent()) {
                TaskModel taskModel = taskModelOptional.get();
                userModel.getListOfTask().remove(taskModel);
                userRepository.save(userModel);
            } else {
                throw new ChangeSetPersister.NotFoundException();
            }
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }


    @Override
    public List<TaskModel> getAllTaskRecords(String userId) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            return userModel.getListOfTask();
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }


    @Override
    public TaskModel updateTaskStatus(String id, TaskStatus status) {
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));
        task.setStatus(status);
        return taskRepository.save(task);
    }


    private boolean isWithinBusinessHours(LocalDateTime currentTime) {
        int currentHour = currentTime.getHour();
        return currentHour >= 8 && currentHour < 17;
    }

    @Override
    public TaskModel startTaskTimer(String userId, String id) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            Optional<TaskModel> taskModelOptional = userModel.getListOfTask().stream()
                    .filter(w -> w.getId().equals(id))
                    .findFirst();

            if (taskModelOptional.isPresent()) {
                TaskModel taskModel = taskModelOptional.get();

                // Check if the timer is already running
                if (taskModel.isTimerRunning()) {
                    throw new IllegalStateException("Timer is already running for this task.");
                }

                // Get the current time and check if it's within business hours
                LocalDateTime currentTime = LocalDateTime.now();
                if (!isWithinBusinessHours(currentTime)) {
                    throw new IllegalStateException("Timer can only be started between 08:00 and 17:00.");
                }

                // Start the timer
                taskModel.setTimerStartTime(currentTime);
                taskModel.setTimerRunning(true);
                userRepository.save(userModel);

                // Automatically stop the timer at 17:00 if it is still running
                scheduleAutomaticStopAtFivePM(userModel, taskModel);

                return taskModel;
            } else {
                throw new ChangeSetPersister.NotFoundException();
            }
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    private void scheduleAutomaticStopAtFivePM(UserModel userModel, TaskModel taskModel) {
        LocalDateTime stopAtTime = LocalDateTime.of(
                LocalDate.now(), LocalTime.of(17, 0)); // Set to 17:00 today

        // Check if it's already past 17:00 today (for whatever reason)
        if (LocalDateTime.now().isAfter(stopAtTime)) {
            stopAtTime = stopAtTime.plusDays(1); // If so, set it to 17:00 on the next day
        }

        // Schedule task to stop at 17:00
        Runnable stopTimerTask = () -> {
            if (taskModel.isTimerRunning()) {
                taskModel.setTimerEndTime(LocalDateTime.now());
                taskModel.setTimerRunning(false);
                userRepository.save(userModel);
            }
        };

        // You can use something like a `ScheduledExecutorService` here to schedule the task
        // Example:
        long delay = java.time.Duration.between(LocalDateTime.now(), stopAtTime).toMillis();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                stopTimerTask.run();
            }
        }, delay);
    }


    @Override
    public TaskModel stopTaskTimer(String userId, String taskId) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            Optional<TaskModel> taskModelOptional = userModel.getListOfTask().stream()
                    .filter(w -> w.getId().equals(taskId))
                    .findFirst();

            if (taskModelOptional.isPresent()) {
                TaskModel taskModel = taskModelOptional.get();
                if (!taskModel.isTimerRunning()) {
                    throw new IllegalStateException("Timer is not running for this task.");
                }
                taskModel.setTimerEndTime(LocalDateTime.now());
                taskModel.setTimerRunning(false);
                userRepository.save(userModel);
                return taskModel;
            } else {
                throw new ChangeSetPersister.NotFoundException();
            }
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }




}
