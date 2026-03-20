package dev.drezolis.RecipeForge.controller;

import dev.drezolis.RecipeForge.service.ChatGptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RecipeController {

    private final ChatGptService chatGptService;

    public RecipeController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @GetMapping
    public Mono<ResponseEntity<String>> generateRecipe() {
        return chatGptService.generateRecipe();
    }
}
