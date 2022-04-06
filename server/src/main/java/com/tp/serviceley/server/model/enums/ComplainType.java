package com.tp.serviceley.server.model.enums;

public enum ComplainType {
    OrderRelated,
    PaymentRelated,
    ServiceDeliveryRelated,
    ServiceProviderRelated;
    /*
    OrderRelated -           If you think some item is missing in your order, or you don't get coupon or offer benefit.
    PaymentRelated -         If you think you made payment, but it is not displaying in your account.
    ServiceDeliveryRelated - If you think you were not provided service on some days but in your account it is showing
                             as you were provided services.
    ServiceProviderRelated - If You are not satisfied with the service provider behaviour, services etc. or you
                             have doubt on his/her character etc.
     */
}
