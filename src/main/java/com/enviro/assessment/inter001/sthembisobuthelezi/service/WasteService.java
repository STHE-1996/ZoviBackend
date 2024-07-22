package com.enviro.assessment.inter001.sthembisobuthelezi.service;

import com.enviro.assessment.inter001.sthembisobuthelezi.model.WasteModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.UpdateWasteRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.WasteRequest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface WasteService {
    WasteModel createWasteRecord(WasteRequest request) throws ChangeSetPersister.NotFoundException;

    WasteModel updateWasteRecord(UpdateWasteRequest request) throws ChangeSetPersister.NotFoundException, ChangeSetPersister.NotFoundException;

    void deleteWasteRecord(String userId, String wasteId) throws ChangeSetPersister.NotFoundException;

    List<WasteModel> getAllWasteRecords(String userId) throws ChangeSetPersister.NotFoundException;
}
