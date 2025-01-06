package com.pio.foodiepanda.dto;

import java.time.LocalDateTime;

public class LoyaltyPointsDTO extends BaseDTO {

    private Long loyaltyPointsId;
    private Long customerId;
    private Integer totalPoints;
    private Integer redeemedPoints;
    private LocalDateTime earnedOn;
    private LocalDateTime expiryDate;
    private LocalDateTime redeemedAt;
    private String status;

    public Long getLoyaltyPointsId() {
        return loyaltyPointsId;
    }

    public void setLoyaltyPointsId(Long loyaltyPointsId) {
        this.loyaltyPointsId = loyaltyPointsId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
