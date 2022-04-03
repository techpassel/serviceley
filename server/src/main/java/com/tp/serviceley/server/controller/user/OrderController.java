package com.tp.serviceley.server.controller.user;

import com.tp.serviceley.server.dto.OrderRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.user.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/order")
public class OrderController {
    private final OrderService orderService;

    @RequestMapping(method = RequestMethod.POST, value="")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        try{
            return new ResponseEntity<>(orderService.createOrder(orderRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value="/request-cancel")
    public ResponseEntity<?> requestCancelOrder(@RequestBody Map<String, Object> data){
        try{
            return new ResponseEntity<>(orderService.requestCancelOrder(data), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Cancel order API is defined in StaffController as only staff and admin can cancel an order.
}
