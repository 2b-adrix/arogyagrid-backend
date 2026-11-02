package com.arogyagrid.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AiChatRequest {
    @NotBlank(message = "Prompt is required")
    private String prompt;

    private String context;
}