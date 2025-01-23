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

    Logger logger = Logger.getLogger(RegistrationServiceImpl.class.getName());
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ModelMapper modelMapper;

    /*
     * Creates a new coupon  based on given data
     * @param : the DTO contain the coupon information
     * @throws : throws the exception when Restaurant with that ID not found
     */
    @Override
    public Coupon createCoupon(CouponDTO couponDTO) {
        Restaurant restaurant = restaurantRepository.findById(couponDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.RESTAURANT_NOT_FOUND + couponDTO.getRestaurantId()));
        logger.log(Level.INFO, MessageConstant.RESTAURANT_FOUND + restaurant.getName());
        Coupon coupon = modelMapper.map(couponDTO, Coupon.class);
        if (coupon.getCouponStatus() == null) {
            coupon.setCouponStatus(CouponStatus.ACTIVE);
        }
        coupon.setRestaurant(restaurant);
        return couponRepository.save(coupon);
    }

    @Override
    public List<CouponDTO> getCouponsByOwner(String ownerUsername) {
        List<Restaurant> restaurants = restaurantRepository.findByRestaurantOwnerUsername(ownerUsername);

        List<Coupon> coupons = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            coupons.addAll(couponRepository.findByRestaurant_RestaurantId(restaurant.getRestaurantId()));
        }
        return coupons.stream().map(coupon ->
                        modelMapper.map(coupon, CouponDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateCouponStatus(Long id, String ownerUsername, CouponStatus newStatus) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));
        if (!coupon.getRestaurant().getRestaurantOwner().getUser().getEmail().equals(ownerUsername)) {
            throw new RuntimeException("You are not authorized owner");
        }
        coupon.setCouponStatus(newStatus);
        couponRepository.save(coupon);
    }


}
