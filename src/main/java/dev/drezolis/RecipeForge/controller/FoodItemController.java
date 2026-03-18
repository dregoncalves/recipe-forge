package dev.drezolis.RecipeForge.controller;

import dev.drezolis.RecipeForge.dto.FoodItemDTO;
import dev.drezolis.RecipeForge.service.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    // GET
    @GetMapping
    public ResponseEntity<List<FoodItemDTO>> get() {
        return ResponseEntity.ok(foodItemService.getAll());
    }

    // GETById
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        FoodItemDTO food = foodItemService.getById(id);
        if (food != null) {
            return ResponseEntity.ok(food);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Objeto não encontrado");
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<FoodItemDTO> create(@RequestBody FoodItemDTO foodItemDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodItemService.createFood(foodItemDTO));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<FoodItemDTO> update(@PathVariable Long id, @RequestBody FoodItemDTO foodItemDTO) {
        return ResponseEntity.ok(foodItemService.updateFood(id, foodItemDTO));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        foodItemService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }
}
