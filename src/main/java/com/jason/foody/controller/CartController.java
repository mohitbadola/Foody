package com.jason.foody.controller;

import com.jason.foody.entity.CartItem;
import com.jason.foody.exception.InvalidIdException;
import com.jason.foody.exception.InvalidOperationException;
import com.jason.foody.service.CartService;
import com.jason.foody.service.FoodItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItem> addCartItem(@Valid @RequestBody CartItem cartItem) throws InvalidIdException {
        CartItem savedItem = cartService.addCart(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<CartItem>> getCartItem(@PathVariable UUID userId){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.fetchUserCartItems(userId));
    }

    @DeleteMapping("/remove/{userId}/{cartItemId}")
    public ResponseEntity<CartItem> removeCartItem(@PathVariable UUID userId,@PathVariable Long cartItemId) throws InvalidOperationException, InvalidIdException {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.removeCartItem(cartItemId, userId));
    }

    @PostMapping("/update")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody @Valid CartItem cartItem) throws InvalidIdException, InvalidOperationException {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.updateCartItem(cartItem));
    }

}
