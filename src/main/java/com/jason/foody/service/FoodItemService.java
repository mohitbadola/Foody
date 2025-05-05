package com.jason.foody.service;

import com.jason.foody.entity.FoodItem;
import com.jason.foody.exception.InvalidIdException;
import com.jason.foody.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FoodItemService {

    @Autowired
    FoodItemRepository repository;

    public UUID save(FoodItem foodItem){
        foodItem.setCreateDate(LocalDate.now());
        FoodItem savedItem = repository.save(foodItem);
        return savedItem.getId();
    }

    public List<FoodItem> getAllFoodItems(){
        return repository.findAll();
    }

    public FoodItem updateFoodItem(FoodItem foodItem) throws InvalidIdException {
        FoodItem item = getFoodItemById(foodItem.getId());
        foodItem.setUpdateDate(LocalDate.now());
        repository.save(foodItem);
        return foodItem;
    }

    public FoodItem getFoodItemById(UUID id) throws InvalidIdException {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Invalid id: " + id));
    }

    public void deleteFoodItem(FoodItem foodItem){
        repository.delete(foodItem);
    }
}
