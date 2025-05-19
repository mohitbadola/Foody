package com.jason.foody.controller;

import com.jason.foody.entity.FoodItem;
import com.jason.foody.exception.InvalidIdException;
import com.jason.foody.service.FoodItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/food-item")
public class FoodItemController {

    @Autowired
    FoodItemService foodItemService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<UUID> addFoodItem(@Valid @RequestBody FoodItem foodItem){
        UUID id = foodItemService.save(foodItem);
        return new ResponseEntity<>(id,HttpStatus.CREATED);
    }

    @GetMapping("/all")
    ResponseEntity<List<FoodItem>> getAllFoodItems(){
        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }


    @GetMapping("/get/{id}")
    ResponseEntity<FoodItem> getFoodItemById(@PathVariable UUID id) throws InvalidIdException {
            FoodItem item = foodItemService.getFoodItemById(id);
            return new ResponseEntity<>(item, HttpStatus.OK);

    }

    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteFoodItem(@RequestBody FoodItem foodItem){
        foodItemService.deleteFoodItem(foodItem);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    ResponseEntity<FoodItem> updateFoodItem(@Valid @RequestBody FoodItem foodItem) throws InvalidIdException {
        FoodItem updatedFoodItem = foodItemService.updateFoodItem(foodItem);
        return new ResponseEntity<>(updatedFoodItem,HttpStatus.OK);
    }


}
