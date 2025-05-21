package com.jason.foody.service;

import com.jason.foody.entity.CartItem;
import com.jason.foody.entity.FoodItem;
import com.jason.foody.entity.Member;
import com.jason.foody.exception.InvalidIdException;
import com.jason.foody.exception.InvalidOperationException;
import com.jason.foody.repository.CartRepository;
import com.jason.foody.repository.FoodItemRepository;
import com.jason.foody.repository.MemberRepository;
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

    public CartItem addCart(CartItem cartItem) throws InvalidIdException {
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

    public CartItem updateCartItem(CartItem cartItem) throws InvalidIdException, InvalidOperationException {
        CartItem item = cartRepository.findById(cartItem.getId()).orElseThrow(
                () -> new InvalidIdException("Invalid cart Item id: " + cartItem.getId() + ".")
        );

        if (!cartItem.getUserId().equals(item.getUserId()) || !cartItem.getItemId().equals(item.getItemId())) {
            throw new InvalidIdException("Invalid user or item id");
        }

        if (item.getQuantity() == cartItem.getQuantity()) {
            throw new InvalidOperationException("Can't update as item quantity is already " + item.getQuantity());
        }

        item.setQuantity(cartItem.getQuantity());
        return cartRepository.save(item);
    }

    public List<CartItem> fetchUserCartItems(UUID userId) {
        return cartRepository.findByUserId(userId);
    }

    public CartItem removeCartItem(Long cartItemId, UUID userId) throws InvalidIdException, InvalidOperationException {
        CartItem cartItem = cartRepository.findById(cartItemId).orElseThrow(
                () -> new InvalidIdException("Invalid cart item id: " + cartItemId + ".")
        );

        if (!cartItem.getUserId().equals(userId)) {
            throw new InvalidOperationException("User doesn't have this item in their cart.");
        }

        cartRepository.deleteById(cartItemId);
        return cartItem;
    }
}
