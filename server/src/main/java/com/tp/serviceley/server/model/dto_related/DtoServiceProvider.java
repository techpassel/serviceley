package com.tp.serviceley.server.model.dto_related;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoServiceProvider {
    private Long id;
    private DtoUser user;
}
