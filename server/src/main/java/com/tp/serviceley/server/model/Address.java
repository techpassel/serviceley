package com.tp.serviceley.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "pin_code", nullable = false)
    private Integer pincode;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    private String area;

    private String landmark;

    @NotBlank
    private String address;

    private Double latitude;

    private Double longitude;

    @Column(name = "gps_details")
    private String gpsDetails;

    @Column(name = "is_default")
    private Boolean isDefaultAddress;
}
