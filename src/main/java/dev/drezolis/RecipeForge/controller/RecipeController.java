package dev.drezolis.RecipeForge.controller;

import dev.drezolis.RecipeForge.dto.FoodItemDTO;
import dev.drezolis.RecipeForge.mapper.FoodItemMapper;
import dev.drezolis.RecipeForge.model.FoodItem;
import dev.drezolis.RecipeForge.service.FoodItemService;
import dev.drezolis.RecipeForge.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RecipeController {

    private final FoodItemMapper foodItemMapper;
    private final FoodItemService foodItemService;
    private final GeminiService geminiService;

    public RecipeController(FoodItemMapper foodItemMapper, FoodItemService foodItemService, GeminiService geminiService) {
        this.foodItemService = foodItemService;
        this.geminiService = geminiService;
        this.foodItemMapper = foodItemMapper;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe() {
        List<FoodItemDTO> foodItems = foodItemService.getAll();

        List<FoodItem> itemsForGemini = foodItems.stream()
                .map(foodItemMapper::toModel)
                .toList();

        return geminiService.generateRecipe(itemsForGemini)
                .map(recipe -> ResponseEntity.ok(recipe))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
