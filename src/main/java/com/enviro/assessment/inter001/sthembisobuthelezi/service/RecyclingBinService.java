package com.enviro.assessment.inter001.sthembisobuthelezi.service;

import com.enviro.assessment.inter001.sthembisobuthelezi.model.RecyclingBinLocations;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.RecyclingRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.UpdateRecyclingRequest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface RecyclingBinService {
    RecyclingBinLocations createRecyclingLocation(RecyclingRequest request) throws ChangeSetPersister.NotFoundException;

    RecyclingBinLocations updateRecyclingLocation(UpdateRecyclingRequest request) throws ChangeSetPersister.NotFoundException;

    void deleteRecyclingLocation(String userId, String recyclingId) throws ChangeSetPersister.NotFoundException;

    List<RecyclingBinLocations> getAllRecycling(String userId) throws ChangeSetPersister.NotFoundException;

    List<RecyclingBinLocations> getAllRecycling();
}
