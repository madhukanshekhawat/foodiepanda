package com.pio.foodiepanda.model;

import com.pio.foodiepanda.enums.CouponApplicableTo;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CouponId;

    @Column(unique = true, nullable = false)
    private String code;

    private Double discountPercentage;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private Double minOrderValue;
    private Integer usageLimit;

    @Enumerated(EnumType.STRING)
    private CouponApplicableTo applicableTo;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

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

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
