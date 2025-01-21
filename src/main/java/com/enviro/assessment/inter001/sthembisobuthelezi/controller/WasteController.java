package com.enviro.assessment.inter001.sthembisobuthelezi.controller;

import com.enviro.assessment.inter001.sthembisobuthelezi.model.WasteModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.UpdateStatusRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.UpdateWasteRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.WasteRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.service.WasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WasteController {
    @Autowired
    private WasteService wasteService;
    @PostMapping("/CreateWaste")
    private WasteModel CreateWaste(@RequestBody WasteRequest request) throws ChangeSetPersister.NotFoundException {
        return wasteService.createWasteRecord(request);
    }

    @PostMapping("/UpdateWaste")
    private WasteModel UpdateWaste(@RequestBody UpdateWasteRequest request) throws ChangeSetPersister.NotFoundException {
        return wasteService.updateWasteRecord(request);
    }


    @DeleteMapping("/deleteWaste/{userId}/{wasteId}")
    public ResponseEntity<String> deleteWasteRecord(@PathVariable String userId, @PathVariable String wasteId) {
        try {
            wasteService.deleteWasteRecord(userId, wasteId);
            return ResponseEntity.ok("Waste record deleted successfully.");
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/GetAllWaste/{userId}")
    public ResponseEntity<?> getAllWasteRecords(@PathVariable String userId) {
        try {
            List<WasteModel> wasteRecords = wasteService.getAllWasteRecords(userId);
            return ResponseEntity.ok(wasteRecords);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/updateStatus")
    public WasteModel updateWasteStatus(@RequestBody UpdateStatusRequest request) {
        return wasteService.updateWasteStatus(request.getId(), request.getStatus());
    }
}
