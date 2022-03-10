package com.tp.serviceley.server.service.user;

import com.tp.serviceley.server.dto.AddressRequestDto;
import com.tp.serviceley.server.dto.AddressResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.AddressMapper;
import com.tp.serviceley.server.model.Address;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.repository.AddressRepository;
import com.tp.serviceley.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private AddressMapper addressMapper;
    private AddressRepository addressRepository;

    public AddressResponseDto createAddress(AddressRequestDto addressRequestDto) {
        User user = userRepository.findById(addressRequestDto.getUserId()).orElseThrow(() -> new BackendException
                ("User with given userId not found."));

        //Every user must have one and only one default address. We have to ensure it.
        Address userDefaultAddress = addressRepository.findUserDefaultAddress(user);
        if (addressRequestDto.getIsDefaultAddress() != null && addressRequestDto.getIsDefaultAddress() == true) {
            if (userDefaultAddress != null) {
                userDefaultAddress.setIsDefaultAddress(false);
                addressRepository.save(userDefaultAddress);
            }
        } else {
            if(addressRequestDto.getId() != null){
                if(userDefaultAddress.getId() == addressRequestDto.getId()){
                    addressRequestDto.setIsDefaultAddress(true);
                } else {
                    addressRequestDto.setIsDefaultAddress(false);
                }
            } else {
                if(userDefaultAddress == null){
                    addressRequestDto.setIsDefaultAddress(true);
                } else {
                    addressRequestDto.setIsDefaultAddress(false);
                }
            }
        }

        Address address = addressMapper.mapToModel(addressRequestDto, user);
        Address createdAddress = addressRepository.save(address);
        return addressMapper.mapToDto(createdAddress);
    }

    public void deleteAddress(Long id) {
        try {
            Optional<Address> optionalAddress = addressRepository.findById(id);
            if (optionalAddress.isPresent()) {
                Address address = optionalAddress.get();
                if (address.getIsDefaultAddress()) {
                    throw new BackendException("This is your default address. Please declare some other address as " +
                            "default first to delete this address.");
                }
            } else {
                throw new BackendException("Address with given id not found.");
            }
            addressRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Staff with given id doesn't exist.", e);
        }
    }
}
