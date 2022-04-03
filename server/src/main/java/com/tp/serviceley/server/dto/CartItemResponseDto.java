package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.dto_related.DtoOffer;
import com.tp.serviceley.server.model.dto_related.DtoServiceFrequency;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import com.tp.serviceley.server.model.dto_related.DtoServiceUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto {
    private Long id;
    private Long cartId;
    private ServiceType serviceType;
    private DtoServiceSubtype serviceSubtype;
    private Integer quantity;
    private DtoServiceUnit serviceUnit;
    private DtoServiceFrequency serviceFrequency;
    private LocalDate serviceFromDate;
    private LocalDate serviceUptoDate;
}
