package dev.drezolis.RecipeForge.service;

import dev.drezolis.RecipeForge.model.FoodItem;
import dev.drezolis.RecipeForge.records.GeminiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeminiService {

    private final WebClient webClient;
    private final String apiKey = System.getenv("GEMINI_API_KEY");

    public GeminiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(List<FoodItem> foodItems) {
        String alimentos = foodItems.stream()
                .map(foodItem -> String.format("%s (%s) - Quantidade, Validade: %s, Category: %s",
                        foodItem.getName(),
                        foodItem.getQuantity(),
                        foodItem.getExpiryDate(),
                        foodItem.getCategory()))
                .collect(Collectors.joining("\n"));

        String prompt = "Baseado nos itens do meu banco de dados, gere uma receita: " + alimentos;

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                ),
                "system_instruction", Map.of(
                        "parts", List.of(
                                Map.of("text", "Se não houver alimentos cadastrados no banco de dados, retorne 'Você não cadastrou nenhum alimento.'")
                        )
                )
        );

        return webClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent")
                .header("x-goog-api-key", apiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .map(response -> {
                    if (response.candidates() != null && !response.candidates().isEmpty()) {
                        return response.candidates().get(0).content().parts().get(0).text();
                    }
                    return "Nenhuma receita foi gerada.";
                });
    }
}

