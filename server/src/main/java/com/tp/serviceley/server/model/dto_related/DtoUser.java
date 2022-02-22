package com.tp.serviceley.server.model.dto_related;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {
    private Long id;
    private String firstName;
    private String lastName;
}
