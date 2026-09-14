package com.example.essayweb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

record EssaySummaryDto(Integer id, String title,
                       @JsonProperty("created_at") LocalDateTime createdAt) {
}

record EssayDetailDto(Integer id, String title, String content,
                      @JsonProperty("created_at") LocalDateTime createdAt) {
}

record CreateEssayRequest(String title, String content) {
}
