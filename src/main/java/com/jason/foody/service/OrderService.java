package com.jason.foody.service;

import com.jason.foody.entity.FoodItem;
import com.jason.foody.entity.Member;
import com.jason.foody.entity.Order;
import com.jason.foody.entity.OrderItem;
import com.jason.foody.exception.InvalidIdException;
import com.jason.foody.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final FoodItemService foodItemService;

    public OrderService(OrderRepository orderRepository, MemberService memberService, FoodItemService foodItemService) {
        this.orderRepository = orderRepository;
        this.memberService = memberService;
        this.foodItemService = foodItemService;
    }

    public Order placeOrder(Order order) throws InvalidIdException {
        Member member = memberService.getMemberById(order.getMember().getId());
        double amount = 0.0;

        for(OrderItem orderItem : order.getOrderItems()) {
            FoodItem item = foodItemService.getFoodItemById(orderItem.getFoodItem().getId());
            amount += item.getPrice() * orderItem.getQuantity();
            orderItem.setName(item.getName());
            orderItem.setPrice(item.getPrice());
        }

        order.setAmount(amount);
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> findByMemberId(UUID memberId) {
        return orderRepository.findOrdersByMemberId(memberId);
    }
}
