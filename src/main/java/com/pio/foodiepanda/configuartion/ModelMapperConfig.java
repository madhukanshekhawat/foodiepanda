package com.pio.foodiepanda.configuartion;

import com.pio.foodiepanda.dto.RestaurantOwnerDTO;
import com.pio.foodiepanda.model.RestaurantOwner;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(RestaurantOwner.class, RestaurantOwnerDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getEmail(), RestaurantOwnerDTO::setEmail);
        });

        return modelMapper;
    }
}