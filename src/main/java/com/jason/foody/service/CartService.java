package com.jason.foody.service;

import com.jason.foody.entity.CartItem;
import com.jason.foody.entity.FoodItem;
import com.jason.foody.entity.Member;
import com.jason.foody.exception.InvalidIdException;
import com.jason.foody.repository.CartRepository;
import com.jason.foody.repository.FoodItemRepository;
import com.jason.foody.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final FoodItemRepository foodItemRepository;

    public CartService(CartRepository cartRepository, MemberRepository memberRepository, FoodItemRepository foodItemRepository) {
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
        this.foodItemRepository = foodItemRepository;
    }

    public CartItem addCartItem(CartItem cartItem) throws InvalidIdException {
        Member member = memberRepository.findById(cartItem.getUserId()).orElseThrow(
                () -> new InvalidIdException("User not found with id: " + cartItem.getUserId() + ".")
        );

        FoodItem foodItem = foodItemRepository.findById(cartItem.getItemId()).orElseThrow(
                () -> new InvalidIdException("Food Item not found with id: " + cartItem.getItemId() + ".")
        );
        cartItem.setItemName(foodItem.getName());
        cartItem.setPrice(foodItem.getPrice());
        return cartRepository.save(cartItem);
    }

    public List<CartItem> fetchUserCartItems(UUID userId) {
        return cartRepository.findByUserId(userId);
    }
}
