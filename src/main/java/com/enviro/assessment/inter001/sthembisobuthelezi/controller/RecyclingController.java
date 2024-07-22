package com.enviro.assessment.inter001.sthembisobuthelezi.controller;

import com.enviro.assessment.inter001.sthembisobuthelezi.model.RecyclingBinLocations;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.RecyclingRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.UpdateRecyclingRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.service.RecyclingBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecyclingController {
    @Autowired
    private RecyclingBinService recyclingBinService;

    @PostMapping("/CreateRecyclingLocation")
    private RecyclingBinLocations CreateWaste(@RequestBody RecyclingRequest request) throws ChangeSetPersister.NotFoundException {
        return recyclingBinService.createRecyclingLocation(request);
    }

    @PostMapping("/UpdateRecycling")
    private RecyclingBinLocations UpdateRecycling(@RequestBody UpdateRecyclingRequest request) throws ChangeSetPersister.NotFoundException {
        return recyclingBinService.updateRecyclingLocation(request);
    }


    @DeleteMapping("/deleteRecyclingLocation/{userId}/{recyclingId}")
    public ResponseEntity<String> deleteRecyclingLocation(@PathVariable String userId, @PathVariable String recyclingId) {
        try {
            recyclingBinService.deleteRecyclingLocation(userId, recyclingId);
            return ResponseEntity.ok("RecyclingLocation record deleted successfully.");
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getAllRecycling/{userId}")
    public ResponseEntity<?> getAllRecycling(@PathVariable String userId) {
        try {
            List<RecyclingBinLocations> recyclingBinLocations = recyclingBinService.getAllRecycling(userId);
            return ResponseEntity.ok(recyclingBinLocations);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
