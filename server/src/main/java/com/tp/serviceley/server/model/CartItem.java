package com.tp.serviceley.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_subtype_id")
    private ServiceSubtype serviceSubtype;
    //We have not created field "offer" here but best applicable offer must be applied to every cartItem
    //And even should be shown to user on Client side and when user confirms the order applied offer must 
    //be stored in orderItems.
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_unit_id")
    private ServiceUnit serviceUnit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_frequency_id")
    private ServiceFrequency serviceFrequency;

    @Column(name = "service_from_date")
    private LocalDate serviceFromDate;

    @Column(name = "service_upto_date")
    private LocalDate serviceUptoDate;
    //serviceUpto date can be maximum 12 months after the serviceFromDate.
}
