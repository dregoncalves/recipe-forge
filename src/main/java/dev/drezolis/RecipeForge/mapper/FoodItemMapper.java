package dev.drezolis.RecipeForge.mapper;

import dev.drezolis.RecipeForge.dto.FoodItemDTO;
import dev.drezolis.RecipeForge.model.FoodItem;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface FoodItemMapper {

    FoodItem toModel(FoodItemDTO foodItemDTO);

    FoodItemDTO toDto(FoodItem foodItem);

}
