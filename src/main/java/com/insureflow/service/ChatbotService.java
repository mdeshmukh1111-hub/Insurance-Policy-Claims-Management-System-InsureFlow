package com.insureflow.service;

import com.insureflow.dto.ChatRequestDTO;
import com.insureflow.dto.ChatResponseDTO;

public interface ChatbotService {
    ChatResponseDTO processQuery(ChatRequestDTO request);
}
