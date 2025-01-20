package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.constants.ViewConstant;
import com.pio.foodiepanda.dto.RestaurantOwnerDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.repository.RestaurantOwnerRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.AdminService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.pio.foodiepanda.constants.MessageConstant.OWNER_NOT_FOUND;

@Service
public class AdminServiceImpl implements AdminService {

    Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    /*
     * This method fetches all the unapproved owners from the db
     * @return : returns a list of RestaurantOwnerDTO object representing all unapproved  owners
     */
    @Override
    public List<RestaurantOwnerDTO> getUnApprovedOwners() {
        List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findByIsApprovedFalseAndRejectedFalse();
        return restaurantOwners.stream()
                .map(restaurantOwner -> modelMapper.map(restaurantOwner, RestaurantOwnerDTO.class))
                .collect(Collectors.toList());
    }

    /*
     * This method approves a restaurant owner by setting "isApproved" flag variable true
     * @param : Gets the ID of restaurant owner to approve
     * @throws : throws the exception when owner with that ID not found
     */
    @Override
    public void approveOwner(Long ownerID) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(ownerID)
                .orElseThrow(() -> new ResourceNotFoundException(OWNER_NOT_FOUND + ownerID));
        logger.log(Level.INFO, MessageConstant.RESTAURANT_NOT_FOUND + ownerID);
        restaurantOwner.setApproved(true);
        restaurantOwnerRepository.save(restaurantOwner);
    }

    /*
     * This method deletes a restaurant owner by setting "isApproved" flag variable false
     * @param : Gets the ID of restaurant owner to delete
     * @throws : throws the exception when owner with that ID not found
     */
    @Override
    @Transactional
    public void rejectOwner(Long ownerID) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(ownerID)
                .orElseThrow(() -> new ResourceNotFoundException(OWNER_NOT_FOUND + ownerID));
        logger.log(Level.INFO, MessageConstant.RESTAURANT_NOT_FOUND + ownerID);
        restaurantOwnerRepository.softDeleteOwner(ownerID);
    }

    /*
     * This method fetches all the approved owners from the db
     * @return : returns a list of RestaurantOwnerDTO object representing all approved  owners
     */
    @Override
    public List<RestaurantOwnerDTO> getApprovedOwners() {
        List<RestaurantOwner> approvedOwners = restaurantOwnerRepository.findByIsApprovedTrue();
        return approvedOwners.stream()
                .map(restaurantOwner -> modelMapper.map(restaurantOwner, RestaurantOwnerDTO.class))
                .collect(Collectors.toList());
    }


}
