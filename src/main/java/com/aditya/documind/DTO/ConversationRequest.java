package com.aditya.documind.DTO;

import jakarta.validation.constraints.NotBlank;

public record ConversationRequest(
        @NotBlank(message = "Title is required")
        String title) {

}
