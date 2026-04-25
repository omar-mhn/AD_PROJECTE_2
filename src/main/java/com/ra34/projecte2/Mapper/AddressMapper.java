package com.ra34.projecte2.Mapper;

import com.ra34.projecte2.DTO.AddressDTO;
import com.ra34.projecte2.DTO.AddressRequest;
import com.ra34.projecte2.Model.Address;

public class AddressMapper {
    public Address toEntity(AddressRequest request) {
        return new Address(
            request.getAddress(),
            request.getCity(),
            request.getPostalCode(),
            request.getCountry(),
            request.getIsDefault()
        );
    }

    public AddressDTO toDTO(Address entity) {
        return new AddressDTO(
            entity.getId(),
            entity.getAddress(),
            entity.getCity(),
            entity.getPostalCode(),
            entity.getCountry(),
            entity.getIsDefault()
        );
    }
}
