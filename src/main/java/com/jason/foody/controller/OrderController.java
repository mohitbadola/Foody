package com.jason.foody.controller;

import com.jason.foody.entity.Member;
import com.jason.foody.entity.Order;
import com.jason.foody.exception.InvalidIdException;
import com.jason.foody.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) throws InvalidIdException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(order));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<Order>> findOrderById(@PathVariable UUID memberId){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findByMemberId(memberId));
    }
}
