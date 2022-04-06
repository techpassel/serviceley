package com.tp.serviceley.server.model.dto_related;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoOrder {
    private Long id;
    private String displayOrderId;
    private LocalDateTime createdAt;
}
