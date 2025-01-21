package com.enviro.assessment.inter001.sthembisobuthelezi.service.implemenation;

import com.enviro.assessment.inter001.sthembisobuthelezi.model.WasteModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.repository.UserRepository;
import com.enviro.assessment.inter001.sthembisobuthelezi.repository.WasteRepository;
import com.enviro.assessment.inter001.sthembisobuthelezi.service.CommonService;
import com.enviro.assessment.inter001.sthembisobuthelezi.model.UserModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.UpdateWasteRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.WasteRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.service.WasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WasteServiceImplementation implements WasteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WasteRepository wasteRepository;

    @Autowired
    private CommonService commonService;


    private static final Set<String> generatedNumbers = new HashSet<>();
    private static final Random random = new Random();
    @Override
    public WasteModel createWasteRecord(WasteRequest request) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(request.getUserId());

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            WasteModel wasteModel = new WasteModel();

            String wasteId =  generateUniqueNumber();
            wasteModel.setId(wasteId);
            wasteModel.setType(request.getType());
            wasteModel.setQuantity(request.getQuantity());
            wasteModel.setDate(new Date().toString());
            wasteModel.setUserRole(request.getUserRole());
            wasteModel.setLocation(request.getLocation());
            wasteModel.setDayOfRecycling(request.getDayOfRecycling());

            userModel.getListOfWaste().add(wasteModel);
            userRepository.save(userModel);
            return wasteModel;
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }


    @Override
    public WasteModel updateWasteRecord(UpdateWasteRequest request) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(request.getUserId());

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            Optional<WasteModel> wasteModelOptional = userModel.getListOfWaste().stream()
                    .filter(w -> w.getId().equals(request.getWasteId()))
                    .findFirst();

            if (wasteModelOptional.isPresent()) {
                WasteModel wasteModel = wasteModelOptional.get();
                wasteModel.setType(request.getType());
                wasteModel.setQuantity(request.getQuantity());
                wasteModel.setDate(new Date().toString());
                wasteModel.setUserRole(request.getUserRole());
//                wasteModel.setLocation(request.getLocation());
//                wasteModel.setDay(request.getDay());
                userRepository.save(userModel);
                return wasteModel;
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
    public void deleteWasteRecord(String userId, String wasteId) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            Optional<WasteModel> wasteModelOptional = userModel.getListOfWaste().stream()
                    .filter(w -> w.getId().equals(wasteId))
                    .findFirst();

            if (wasteModelOptional.isPresent()) {
                WasteModel wasteModel = wasteModelOptional.get();
                userModel.getListOfWaste().remove(wasteModel);
                userRepository.save(userModel);
            } else {
                throw new ChangeSetPersister.NotFoundException();
            }
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }


    @Override
    public List<WasteModel> getAllWasteRecords(String userId) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            return userModel.getListOfWaste();
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }


    @Override
    public WasteModel updateWasteStatus(String id, String status) {
        WasteModel waste = wasteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Waste not found with ID: " + id));
        waste.setStatus(status);
        return wasteRepository.save(waste);
    }



}
