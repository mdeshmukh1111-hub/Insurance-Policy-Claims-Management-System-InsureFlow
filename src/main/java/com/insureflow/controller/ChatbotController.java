package com.insureflow.controller;

import com.insureflow.dto.ChatRequestDTO;
import com.insureflow.dto.ChatResponseDTO;
import com.insureflow.service.ChatbotService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping
    public ResponseEntity<ChatResponseDTO> processChatQuery(@Valid @RequestBody ChatRequestDTO request) {
        ChatResponseDTO response = chatbotService.processQuery(request);
        return ResponseEntity.ok(response);
    }
}
