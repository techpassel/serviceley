package com.tp.serviceley.server.service.user;

import com.tp.serviceley.server.dto.CartItemRequestDto;
import com.tp.serviceley.server.dto.CartResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.CartItemMapper;
import com.tp.serviceley.server.mapper.CartMapper;
import com.tp.serviceley.server.model.*;

import com.tp.serviceley.server.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceSubtypeRepository serviceSubtypeRepository;
    private final ServiceUnitRepository serviceUnitRepository;
    private final ServiceFrequencyRepository serviceFrequencyRepository;
    private final CartItemMapper cartItemMapper;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;

    @Async
    public void createCart(User user) {
        if (cartRepository.findByUser(user).isEmpty()) {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
    }

    public CartResponseDto addCartItem(CartItemRequestDto cartItemRequestDto) {
        Cart cart = cartRepository.findById(cartItemRequestDto.getCartId()).orElseThrow(() -> new BackendException
                ("Cart not found."));
        if(cartItemRequestDto.getId() != null){
            //It basically means it is an update request and not a request to add new data.
            CartItem oldCartItemData = cart.getItems().stream().filter(item -> item.getId() == cartItemRequestDto
                    .getId()).findFirst().orElseThrow(() -> new BackendException("Cart Item not found"));
            //We don't want to allow updating ServiceType and ServiceSubtype so applying following checks.
            if(oldCartItemData.getServiceType().getId() != cartItemRequestDto.getServiceTypeId()){
                throw new BackendException("Service type can't be changed.");
            }
            if(oldCartItemData.getServiceSubtype().getId() != cartItemRequestDto.getServiceSubtypeId()){
                throw new BackendException("Service subtype can't be changed.");
            }
        }
        ServiceType serviceType = serviceTypeRepository.findById(cartItemRequestDto.getServiceTypeId())
                .orElseThrow(() -> new BackendException("Service Type not found."));
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.findById(cartItemRequestDto.getServiceSubtypeId())
                .orElseThrow(() -> new BackendException("Service subtype not found."));
        ServiceUnit serviceUnit = serviceUnitRepository.findById(cartItemRequestDto.getServiceUnitId())
                .orElseThrow(() -> new BackendException("Service unit not found"));
        ServiceFrequency serviceFrequency = serviceFrequencyRepository.findById(cartItemRequestDto.getServiceFrequencyId())
                .orElseThrow(() -> new BackendException("Service frequency not found"));
        CartItem cartItem = cartItemMapper.mapToModel(cartItemRequestDto, cart, serviceType, serviceSubtype, serviceUnit, serviceFrequency);
        List<CartItem> cartItems = cart.getItems();
        cartItems.add(cartItem);
        cart.setItems(cartItems);
        return cartMapper.mapToDto(cartRepository.save(cart));
    }

    public CartResponseDto removeCartItem(Long cartId, Long cartItemId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new BackendException
                ("Cart not found."));
        List<CartItem> cartItems = cart.getItems();
        CartItem toRemoveCartItem = cartItems.stream().filter(item -> item.getId() == cartItemId).findFirst().
                orElseThrow(() -> new BackendException("Cart item not found."));
        cartItems.remove(toRemoveCartItem);
        cart.setItems(cartItems);
        return cartMapper.mapToDto(cartRepository.save(cart));
    }

    public CartResponseDto getUserCartDetails(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new BackendException("Cart not found"));
        return cartMapper.mapToDto(cart);
    }
}
