package com.tp.serviceley.server.model.enums;

public enum ServiceUnitType {
    Basic,
    AdditionalPerUnit,
    PerUnitBasis;
    /*
        A service can be either a combination of types - Basic + AdditionalPerUnit or of type PerUnitBasis
        For example - For service subtype "Home cleaning" there will be two service unit type -
        1. Basic - For upto 1000 sq. ft. area
        2. AdditionalPerUnit - For every additional 500sq. ft.
        And for service subtype "Elderly/Patient Care" there will be only one unit type -
        1. PerUnitBasis - For every Elderly person or Patient for which service is required
    */
}
