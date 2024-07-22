package com.enviro.assessment.inter001.sthembisobuthelezi.service.implemenation;

import com.enviro.assessment.inter001.sthembisobuthelezi.requests.RecyclingRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.UpdateRecyclingRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.service.RecyclingBinService;
import com.enviro.assessment.inter001.sthembisobuthelezi.enums.UserRole;
import com.enviro.assessment.inter001.sthembisobuthelezi.model.RecyclingBinLocations;
import com.enviro.assessment.inter001.sthembisobuthelezi.model.UserModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecyclingBinServiceImplementation implements RecyclingBinService {

@Autowired
private UserRepository userRepository;
    @Override
    public RecyclingBinLocations createRecyclingLocation(RecyclingRequest request) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(request.getUserId());

        if (userModelOptional.isPresent()) {
            if (userModelOptional.get().getUserRole().equals(UserRole.waste_management_staff)){
                UserModel userModel = userModelOptional.get();

                RecyclingBinLocations recyclingBinLocations = new RecyclingBinLocations();
                recyclingBinLocations.setType(request.getType());
                recyclingBinLocations.setLocation(request.getLocation());
                recyclingBinLocations.setAvailability(request.getAvailability());

                userModel.getRecyclingBinLocationsList().add(recyclingBinLocations);
                userRepository.save(userModel);
                return recyclingBinLocations;
            }

        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
        return null;
    }

    @Override
    public RecyclingBinLocations updateRecyclingLocation(UpdateRecyclingRequest request) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(request.getUserId());

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();


            if (userModel.getUserRole().equals(UserRole.waste_management_staff)) {

                Optional<RecyclingBinLocations> recyclingBinLocationsOptional = userModel.getRecyclingBinLocationsList().stream()
                        .filter(w -> w.getId().equals(request.getRecyclingId()))
                        .findFirst();

                if (recyclingBinLocationsOptional.isPresent()) {
                    RecyclingBinLocations recyclingBinLocation = recyclingBinLocationsOptional.get();
                    recyclingBinLocation.setType(request.getType());
                    recyclingBinLocation.setLocation(request.getLocation());
                    recyclingBinLocation.setAvailability(request.getAvailability());

                    userRepository.save(userModel);
                    return recyclingBinLocation;
                } else {
                    throw new ChangeSetPersister.NotFoundException();
                }
            } else {
                throw new ChangeSetPersister.NotFoundException();
            }
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @Override
    public void deleteRecyclingLocation(String userId, String recyclingId) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            if (userModel.getUserRole().equals(UserRole.waste_management_staff)) {
            Optional<RecyclingBinLocations> recyclingBinLocations = userModel.getRecyclingBinLocationsList().stream()
                    .filter(w -> w.getId().equals(recyclingId))
                    .findFirst();

            if (recyclingBinLocations.isPresent()) {
                RecyclingBinLocations recyclingBinLocations1 = recyclingBinLocations.get();
                userModel.getRecyclingBinLocationsList().remove(recyclingBinLocations1);
                userRepository.save(userModel);
            } else {
                throw new ChangeSetPersister.NotFoundException();
            }
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }


    @Override
    public List<RecyclingBinLocations> getAllRecycling(String userId) throws ChangeSetPersister.NotFoundException {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            return userModel.getRecyclingBinLocationsList();
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

}
