package com.pio.foodiepanda.model;

import com.pio.foodiepanda.constants.CouponStatus;
import com.pio.foodiepanda.enums.CouponApplicableTo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CouponId;

    @Column(unique = true, nullable = false)
    @Size(max = 10, message = "value can not be greater than 10")
    private String code;

    @Min(value = 5, message = "Discount percentage can not be lesser than 5 ")
    private Double discountPercentage;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;

    @Min(value = 199, message = "Min order value can not be lesser than 199")
    private Double minOrderValue;

    @Min(value = 1, message = "Usage limit can not be lesser than 1")
    private Integer usageLimit;

    @Enumerated(EnumType.STRING)
    private CouponApplicableTo applicableTo;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Long getCouponId() {
        return CouponId;
    }

    public void setCouponId(Long couponId) {
        CouponId = couponId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public Double getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(Double minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public CouponApplicableTo getApplicableTo() {
        return applicableTo;
    }

    public void setApplicableTo(CouponApplicableTo applicableTo) {
        this.applicableTo = applicableTo;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public CouponStatus getCouponStatus() {
        return status;
    }

    public void setCouponStatus(CouponStatus status) {
        this.status = status;
    }
}
