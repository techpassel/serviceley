package com.tp.serviceley.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

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
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_subtype_id")
    private ServiceSubtype serviceSubtype;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_unit_id")
    private ServiceUnit serviceUnit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_frequency_id")
    private ServiceFrequency serviceFrequency;
}
