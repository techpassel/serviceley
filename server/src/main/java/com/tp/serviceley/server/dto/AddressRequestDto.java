package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {
    private Long id;
    private Long userId;
    private Integer pincode;
    private String country;
    private String state;
    private String city;
    private String area;
    private String landmark;
    private String address;
    private Double latitude;
    private Double longitude;
    private String gpsDetails;
    private Boolean isDefaultAddress;
}
