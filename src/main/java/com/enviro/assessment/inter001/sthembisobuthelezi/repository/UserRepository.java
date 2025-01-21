package com.enviro.assessment.inter001.sthembisobuthelezi.repository;

import com.enviro.assessment.inter001.sthembisobuthelezi.model.UserModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.model.WasteModel;
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

    @Query("SELECT u.listOfWaste FROM UserModel u")
    List<WasteModel> findAllWaste();

    UserModel findByEmail(String email);

    Optional<UserModel> findByPin(String enteredPin);
}
