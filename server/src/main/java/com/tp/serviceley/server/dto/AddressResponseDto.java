package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
    private Long id;
    private DtoUser user;
    private Integer pinCode;
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
