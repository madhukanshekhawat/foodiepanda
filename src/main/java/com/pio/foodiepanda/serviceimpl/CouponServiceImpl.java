package com.pio.foodiepanda.serviceimpl;

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

import static com.pio.foodiepanda.constants.Constant.RESTAURANT_NOT_FOUND;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Coupon createCoupon(CouponDTO couponDTO) {

        Restaurant restaurant = restaurantRepository.findById(couponDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException(RESTAURANT_NOT_FOUND + couponDTO.getRestaurantId()));
        Coupon coupon = modelMapper.map(couponDTO, Coupon.class);
        coupon.setRestaurant(restaurant);
        return couponRepository.save(coupon);
    }

}
