package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AddressRepository extends JpaRepository<Address, Long> {

}
