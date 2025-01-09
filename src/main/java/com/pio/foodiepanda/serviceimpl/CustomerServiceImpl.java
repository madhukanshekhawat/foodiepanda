package com.pio.foodiepanda.serviceimpl;

import com.pio.foodiepanda.dto.CustomerDTO;
import com.pio.foodiepanda.model.Customer;
import com.pio.foodiepanda.repository.CustomerRepository;
import com.pio.foodiepanda.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerDTO> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());
            return customerDTO;
        }).toList();
    }
}
