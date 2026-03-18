package dev.drezolis.RecipeForge.dto;

import dev.drezolis.RecipeForge.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemDTO {

    private String name;
    private Category category;
    private Integer quantity;
    private LocalDate expiryDate;

}
