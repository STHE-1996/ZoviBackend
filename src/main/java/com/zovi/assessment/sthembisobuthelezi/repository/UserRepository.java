package com.zovi.assessment.sthembisobuthelezi.repository;

import com.zovi.assessment.sthembisobuthelezi.model.TaskModel;
import com.zovi.assessment.sthembisobuthelezi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {

    @Query("SELECT COUNT(u) FROM UserModel u WHERE u.gender = :gender")
    long countByGender(@Param("gender") String gender);

    @Query("SELECT u.listOfTask FROM UserModel u")
    List<TaskModel> findAllTask();

    UserModel findByEmail(String email);

    Optional<UserModel> findByPin(String enteredPin);
}
