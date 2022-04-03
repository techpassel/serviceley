package com.tp.serviceley.server.service.user;

import com.tp.serviceley.server.dto.OrderItemRequestDto;
import com.tp.serviceley.server.dto.OrderRequestDto;
import com.tp.serviceley.server.dto.OrderResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.OrderItemMapper;
import com.tp.serviceley.server.mapper.OrderMapper;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.model.enums.OrderStatus;
import com.tp.serviceley.server.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final SpecialDiscountRepository specialDiscountRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceSubtypeRepository serviceSubtypeRepository;
    private final ServiceUnitRepository serviceUnitRepository;
    private final ServiceFrequencyRepository serviceFrequencyRepository;
    private final OfferRepository offerRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        User user = userRepository.findById(orderRequestDto.getUserId()).orElseThrow(() -> new
                BackendException("User not found."));
        Coupon coupon = couponRepository.findById(orderRequestDto.getCouponId()).orElseThrow(() -> new
                BackendException("Coupon not found."));
        SpecialDiscount specialDiscount = specialDiscountRepository.findById(orderRequestDto.getSpecialDiscountId())
                .orElseThrow(() -> new BackendException("Special discount not found."));
        List<OrderItem> orderItems = new ArrayList<>();
        Order order = orderRepository.save(orderMapper.mapToModel(orderRequestDto, user, coupon, specialDiscount, orderItems));
        List<OrderItemRequestDto> requestOrderItems = orderRequestDto.getItems();
        orderItems = requestOrderItems.stream().map(item -> {
            ServiceType serviceType = serviceTypeRepository.findById(item.getServiceTypeId())
                    .orElseThrow(() -> new BackendException("Service Type not found for order item" + item.getId()));
            ServiceSubtype serviceSubtype = serviceSubtypeRepository.findById(item.getServiceSubtypeId())
                    .orElseThrow(() -> new BackendException("Service subtype not found for order item" + item.getId()));
            ServiceUnit serviceUnit = serviceUnitRepository.findById(item.getServiceUnitId())
                    .orElseThrow(() -> new BackendException("Service unit not found for order item" + item.getId()));
            ServiceFrequency serviceFrequency = serviceFrequencyRepository.findById(item.getServiceFrequencyId())
                    .orElseThrow(() -> new BackendException("Service frequency not found for order item" + item.getId()));
            Offer offer = offerRepository.findById(item.getOfferId()).orElseThrow(() -> new BackendException
                    ("Offer not found"));
            return orderItemMapper.mapToModel(item, order, serviceType, serviceSubtype, serviceUnit, serviceFrequency, offer);
        }).collect(Collectors.toList());
        order.setItems(orderItems);
        return orderMapper.mapToDto(orderRepository.save(order));
    }

    public String requestCancelOrder(Map<String, Object> data){
        Long orderId = (Long) data.get("id");
        String remark = (String) data.get("remark");
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BackendException("Order not found."));
        if(order.getStatus() == OrderStatus.CancellationRequested){
            throw new BackendException("Order Cancellation is already requested. We are processing your request.");
        }
        if(order.getStatus() == OrderStatus.CancellationRequested){
            throw new BackendException("order is already cancelled.");
        }
        order.setStatus(OrderStatus.CancellationRequested);
        order.setCancellationRequestRemark(remark);
        order.setCancellationRequestedOn(LocalDateTime.now());
        return "Your request for order cancellation is submitted successfully. Our will contact you shortly.";
    }

    public String cancelOrder(Map<String, Object> data){
        Long orderId = (Long) data.get("id");
        String remark = (String) data.get("remark");
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BackendException("Order not found."));
        order.setStatus(OrderStatus.Cancelled);
        order.setCancellationRemark(remark);
        orderRepository.save(order);
        return "Order cancelled successfully.";
    }
}
