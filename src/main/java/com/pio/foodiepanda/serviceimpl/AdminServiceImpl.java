package com.pio.foodiepanda.serviceimpl;

import com.pio.foodiepanda.dto.RestaurantOwnerDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.repository.RestaurantOwnerRepository;
import com.pio.foodiepanda.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pio.foodiepanda.constants.Constant.OWNER_NOT_FOUND;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RestaurantOwnerDTO> getUnApprovedOwners() {
        List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findByIsApprovedFalse();
        return restaurantOwners.stream()
                .map(restaurantOwner -> modelMapper.map(restaurantOwner, RestaurantOwnerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void approveOwner(Long restaurantOwnerId) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(restaurantOwnerId)
                .orElseThrow(() -> new ResourceNotFoundException(OWNER_NOT_FOUND + restaurantOwnerId));
        restaurantOwner.setApproved(true);
        restaurantOwnerRepository.save(restaurantOwner);
    }

    @Override
    public void deleteOwner(Long restaurantOwnerId) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(restaurantOwnerId)
                .orElseThrow(() -> new ResourceNotFoundException(OWNER_NOT_FOUND + restaurantOwnerId));
        restaurantOwnerRepository.delete(restaurantOwner);
    }

    @Override
    public List<RestaurantOwnerDTO> getApprovedOwners() {
        List<RestaurantOwner> approvedOwners = restaurantOwnerRepository.findByIsApprovedTrue();
        return approvedOwners.stream()
                .map(restaurantOwner -> modelMapper.map(restaurantOwner, RestaurantOwnerDTO.class))
                .collect(Collectors.toList());
    }


}
