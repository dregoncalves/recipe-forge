package dev.drezolis.RecipeForge.repository;

import dev.drezolis.RecipeForge.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
