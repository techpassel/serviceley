package com.tp.serviceley.server.controller.user;

import com.tp.serviceley.server.dto.CartItemRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.user.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    public ResponseEntity<?> getUserCartDetails(@PathVariable Long userId){
        try{
            return new ResponseEntity<>(cartService.getUserCartDetails(userId), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/item")
    public ResponseEntity<?> addCartItem(@RequestBody CartItemRequestDto cartItemRequestDto){
        try{
            return new ResponseEntity<>(cartService.addCartItem(cartItemRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/item/{cartId}/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        try {
            return new ResponseEntity<>(cartService.removeCartItem(cartId, cartItemId), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
