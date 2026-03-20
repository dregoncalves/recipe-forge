package dev.drezolis.RecipeForge.service;

import dev.drezolis.RecipeForge.config.WebClientConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ChatGptService {

    private final WebClient webClient;
    private String apiKey = System.getenv("OPENAI_API_KEY");

    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe() {
        String prompt = "teste";
        return webClient.post()
                .uri()
                .header("Authorization", "Bearer " + apiKey)
                .header("Conent-Type", "application/json")
                .bodyValue(prompt)
                .retrieve()
                .bodyToMono(String.class);
    }
}
