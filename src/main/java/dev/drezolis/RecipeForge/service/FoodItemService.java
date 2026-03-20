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

    // TODO: Melhorar mensagem de exceção
    public FoodItemDTO getById(Long id) {
        return foodItemRepository.findById(id)
                .map(foodItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Objeto não encontrado"));
    }

    public FoodItemDTO createFood(FoodItemDTO foodItemDTO) {
        FoodItem foodItem = foodItemMapper.toModel(foodItemDTO);
        foodItem = foodItemRepository.save(foodItem);
        return foodItemMapper.toDto(foodItem);
    }

    // TODO: Melhorar mensagem de exceção
    public FoodItemDTO updateFood(Long id, FoodItemDTO foodItemDTO) {
        FoodItem existing = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objeto não encontrado"));

        existing.setId(id);
        existing.setName(foodItemDTO.getName());
        existing.setCategory(foodItemDTO.getCategory());
        existing.setQuantity(foodItemDTO.getQuantity());
        existing.setExpiryDate(foodItemDTO.getExpiryDate());

        return foodItemMapper.toDto(foodItemRepository.save(existing));
    }

    // TODO: Melhorar mensagem de exceção
    public void deleteFood(Long id) {
       if (!foodItemRepository.existsById(id)) {
           throw new EntityNotFoundException("Objeto não encontrado");
       }
       foodItemRepository.deleteById(id);
    }
}
