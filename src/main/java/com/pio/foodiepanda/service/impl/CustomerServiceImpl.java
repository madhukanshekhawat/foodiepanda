package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.dto.CustomerDTO;
import com.pio.foodiepanda.model.Customer;
import com.pio.foodiepanda.repository.CustomerRepository;
import com.pio.foodiepanda.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /*
     * This method is retrieves all the customer in a List
     * @return: return a list of CustomerDTO object representing all customerss
     */
    @Override
    public List<CustomerDTO> getAll() {
        List<Customer> customers = customerRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }
}
