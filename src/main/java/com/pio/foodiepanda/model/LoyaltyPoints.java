package com.pio.foodiepanda.model;

import com.pio.foodiepanda.utility.LoyaltyStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LoyaltyPoints extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loyaltyPointsId;

    private Integer totalPrice;
    private Integer redeemedPoints;
    private LocalDateTime earnedOn;
    private LocalDateTime expiryDate;
    private LocalDateTime redeemedAt;

    @Enumerated(EnumType.STRING)
    private LoyaltyStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id" , nullable = false)
    private Customer customer;

    public Long getLoyaltyPointsId() {
        return loyaltyPointsId;
    }

    public void setLoyaltyPointsId(Long loyaltyPointsId) {
        this.loyaltyPointsId = loyaltyPointsId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getRedeemedPoints() {
        return redeemedPoints;
    }

    public void setRedeemedPoints(Integer redeemedPoints) {
        this.redeemedPoints = redeemedPoints;
    }

    public LocalDateTime getEarnedOn() {
        return earnedOn;
    }

    public void setEarnedOn(LocalDateTime earnedOn) {
        this.earnedOn = earnedOn;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDateTime getRedeemedAt() {
        return redeemedAt;
    }

    public void setRedeemedAt(LocalDateTime redeemedAt) {
        this.redeemedAt = redeemedAt;
    }

    public LoyaltyStatus getStatus() {
        return status;
    }

    public void setStatus(LoyaltyStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
