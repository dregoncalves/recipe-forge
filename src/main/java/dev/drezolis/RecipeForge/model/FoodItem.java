package dev.drezolis.RecipeForge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "food_item")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O alimento não pode existir sem um nome.")
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull(message = "A quantidade do alimento não pode ser nula.")
    @Min(1)
    private Integer quantity;

    private LocalDate expiryDate;

}
