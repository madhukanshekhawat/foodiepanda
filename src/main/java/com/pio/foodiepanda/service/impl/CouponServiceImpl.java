package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.CouponStatus;
import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CouponDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.Coupon;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.repository.CouponRepository;
import com.pio.foodiepanda.repository.RestaurantRepository;
import com.pio.foodiepanda.service.CouponService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    Logger logger = Logger.getLogger(CouponServiceImpl.class.getName());

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Creates a new coupon based on the given data.
     *
     * @param couponDTO the DTO containing the coupon information.
     * @return the created Coupon object.
     * @throws ResourceNotFoundException if the restaurant with the given ID is not found.
     */
    @Override
    public Coupon createCoupon(CouponDTO couponDTO) {
        logger.info(MessageConstant.CREATING_COUPON_FOR_RESTAURANT + couponDTO.getRestaurantId());
        Restaurant restaurant = restaurantRepository.findById(couponDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESTAURANT_NOT_FOUND + couponDTO.getRestaurantId()));
        logger.log(Level.INFO, MessageConstant.RESTAURANT_FOUND + restaurant.getName());
        Coupon coupon = modelMapper.map(couponDTO, Coupon.class);
        if (coupon.getCouponStatus() == null) {
            coupon.setCouponStatus(CouponStatus.ACTIVE);
        }
        coupon.setRestaurant(restaurant);
        Coupon savedCoupon = couponRepository.save(coupon);
        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + restaurant.getName());
        return savedCoupon;
    }

    /**
     * Retrieves a list of coupons for a given restaurant owner.
     *
     * @param ownerUsername the username of the restaurant owner.
     * @return a list of CouponDTO objects.
     */
    @Override
    public List<CouponDTO> getCouponsByOwner(String ownerUsername) {
        logger.info(MessageConstant.FETCHING_COUPON_FOR_OWNER + ownerUsername);
        List<Restaurant> restaurants = restaurantRepository.findByRestaurantOwnerUsername(ownerUsername);

        List<Coupon> coupons = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            coupons.addAll(couponRepository.findByRestaurant_RestaurantId(restaurant.getRestaurantId()));
        }
        List<CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> modelMapper.map(coupon, CouponDTO.class))
                .collect(Collectors.toList());
        logger.info(MessageConstant.UNAUTHORIZED + ownerUsername);
        return couponDTOs;
    }

    /**
     * Updates the status of a coupon.
     *
     * @param id            the ID of the coupon to update.
     * @param ownerUsername the username of the restaurant owner.
     * @param newStatus     the new status to set for the coupon.
     * @throws ResourceNotFoundException if the coupon is not found.
     * @throws RuntimeException          if the user is not authorized to update the coupon.
     */
    @Override
    public void updateCouponStatus(Long id, String ownerUsername, CouponStatus newStatus) {
        logger.info(MessageConstant.UPDATING_COUPON_FOR_COUPON_ID + id);
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.COUPON_NOT_FOUND));
        if (!coupon.getRestaurant().getRestaurantOwner().getUser().getEmail().equals(ownerUsername)) {
            logger.severe(MessageConstant.UNAUTHORIZED + ownerUsername);
            throw new RuntimeException(MessageConstant.UNAUTHORIZED);
        }
        coupon.setCouponStatus(newStatus);
        couponRepository.save(coupon);
        logger.info(MessageConstant.SUCCESSFUL_MESSAGE + id);
    }
}