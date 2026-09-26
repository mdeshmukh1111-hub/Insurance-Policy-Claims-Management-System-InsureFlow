package com.insureflow.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatResponseDTO {

    private String reply;
    private String intent;
    private List<String> suggestions = new ArrayList<>();
    private Map<String, Object> dataCard;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ChatResponseDTO() {}

    public ChatResponseDTO(String reply, String intent, List<String> suggestions) {
        this.reply = reply;
        this.intent = intent;
        this.suggestions = suggestions;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public Map<String, Object> getDataCard() {
        return dataCard;
    }

    public void setDataCard(Map<String, Object> dataCard) {
        this.dataCard = dataCard;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
