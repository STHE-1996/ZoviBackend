package com.zovi.assessment.sthembisobuthelezi.repository;

import com.zovi.assessment.sthembisobuthelezi.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, String> {
}