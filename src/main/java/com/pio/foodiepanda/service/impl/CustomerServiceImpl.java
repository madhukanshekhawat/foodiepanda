package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CustomerDTO;
import com.pio.foodiepanda.dto.RestaurantDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.Customer;
import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.repository.CustomerRepository;
import com.pio.foodiepanda.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());

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

    /**
     * This method mainly used to get all the information of customer
     * @param principal it is used to get the user which is logged in
     * @return returning the dto which contains all information of customer
     */
    @Override
    public CustomerDTO getCustomerProfile(Principal principal) {
        String email = principal.getName();
        logger.info(MessageConstant.FETCHING_CUSTOMER_PROFILE + email);

        Customer customer = customerRepository.findByCustomerEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.CUSTOMER_NOT_FOUND));

        CustomerDTO dto = new CustomerDTO();
        dto.setEmail(email);
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhoneNumber(customer.getPhoneNumber());

        return dto;
    }

    @Override
    public String updateProfile(Principal principal, CustomerDTO customerDTO) {

        String email = principal.getName();
        Optional<Customer> optionalCustomer = customerRepository.findByCustomerEmail(email);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            // Update the customer's details
            customer.setFirstName(customerDTO.getFirstName());
            customer.setLastName(customerDTO.getLastName());
            customer.setPhoneNumber(customerDTO.getPhoneNumber());

            // Save the updated customer back to the database
            customerRepository.save(customer);

            return "Profile updated successfully";
        } else {
            return "Customer not found";
        }
    }
}
