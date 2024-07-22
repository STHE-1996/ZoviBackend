package com.enviro.assessment.inter001.sthembisobuthelezi.repository;

import com.enviro.assessment.inter001.sthembisobuthelezi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {

    @Query("SELECT COUNT(u) FROM UserModel u WHERE u.gender = :gender")
    long countByGender(@Param("gender") String gender);

    UserModel findByEmail(String email);
}
