package com.arogyagrid.controller;

import com.arogyagrid.dto.request.AiChatRequest;
import com.arogyagrid.dto.response.AiChatResponse;
import com.arogyagrid.dto.response.ApiResponse;
import com.arogyagrid.service.AiAssistantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai-assistant")
@RequiredArgsConstructor
public class AiAssistantController {

    private final AiAssistantService aiService;

    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<AiChatResponse>> chat(
            @Valid @RequestBody AiChatRequest request) {
        AiChatResponse response = aiService.processQuery(request.getPrompt());
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}