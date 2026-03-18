package dev.drezolis.RecipeForge.service;

import dev.drezolis.RecipeForge.dto.FoodItemDTO;
import dev.drezolis.RecipeForge.mapper.FoodItemMapper;
import dev.drezolis.RecipeForge.model.FoodItem;
import dev.drezolis.RecipeForge.repository.FoodItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final FoodItemMapper foodItemMapper;

    public FoodItemService(FoodItemRepository foodItemRepository, FoodItemMapper foodItemMapper) {
        this.foodItemRepository = foodItemRepository;
        this.foodItemMapper = foodItemMapper;
    }

    // TODO: Revisar se o retorno está correto com Optional.of
    public List<FoodItemDTO> getAll() {
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        return foodItemList.stream()
                .map(foodItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public FoodItemDTO getById(Long id) {
        Optional<FoodItem> food = foodItemRepository.findById(id);
        return food.map(foodItemMapper::toDto).orElse(null);
    }

    public FoodItemDTO createFood(FoodItemDTO foodItemDTO) {
        FoodItem foodItem = foodItemMapper.toModel(foodItemDTO);
        foodItem = foodItemRepository.save(foodItem);
        return foodItemMapper.toDto(foodItem);
    }

    public FoodItemDTO updateFood(Long id, FoodItemDTO foodItemDTO) {
        if (foodItemRepository.findById(id).isPresent()) {
            FoodItem foodItem = foodItemMapper.toModel(foodItemDTO);
            foodItem.setId(id);
            FoodItem savedFoodItem = foodItemRepository.save(foodItem);
            return foodItemMapper.toDto(savedFoodItem);
        }
        return null;
    }

    public void deleteFood(Long id) {
        FoodItem foodItem = foodItemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Objeto não encontrado"));
        foodItemRepository.delete(foodItem);
    }
}
