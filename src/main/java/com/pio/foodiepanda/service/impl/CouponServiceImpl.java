package com.pio.foodiepanda.service.impl;

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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.pio.foodiepanda.constants.MessageConstant.RESTAURANT_NOT_FOUND;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = Logger.getLogger(RegistrationServiceImpl.class.getName());

    /*
     * Creates a new coupon  based on given data
     * @param : the DTO contain the coupon information
     * @throws : throws the exception when Restaurant with that ID not found
     */
    @Override
    public Coupon createCoupon(CouponDTO couponDTO) {
        Restaurant restaurant = restaurantRepository.findById(couponDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + couponDTO.getRestaurantId()));
        logger.log(Level.INFO, "Restaurant found: " + restaurant.getName());
        Coupon coupon = modelMapper.map(couponDTO, Coupon.class);
        coupon.setRestaurant(restaurant);
        return couponRepository.save(coupon);
    }

//    @Override
//    public List<CouponDTO> getCouponByRestaurantId(Long restaurantId) {
//        return couponRepository.findByRestaurant_RestaurantId(restaurantId).stream()
//                .map(coupon -> modelMapper.map(coupon, CouponDTO.class))
//                .collect(Collectors.toList());
//    }

}
