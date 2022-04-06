package com.tp.serviceley.server.model.dto_related;

import com.tp.serviceley.server.model.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoOrderItem {
    private Long id;
    private DtoOrder order;
    private ServiceType serviceType;
    private DtoServiceSubtype serviceSubtype;
}
