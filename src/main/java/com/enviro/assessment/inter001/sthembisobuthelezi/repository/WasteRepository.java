package com.enviro.assessment.inter001.sthembisobuthelezi.repository;

import com.enviro.assessment.inter001.sthembisobuthelezi.model.WasteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteRepository extends JpaRepository<WasteModel, String> {
}