package com.history_expert.history.config;

import com.history_expert.history.advisor.TokenUsageAuditAdvisor;
import com.history_expert.history.rag.WebSearchDocumentRetriever;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WebSearchRagConfig {
  @Bean("webSearchRAGChatClient")
  public ChatClient chatClient(
      ChatClient.Builder chatClientBuilder,
      ChatMemory chatMemory,
      RestClient.Builder restClientBuilder) {
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
    Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
    var webSearchRAGAdvisor =
        RetrievalAugmentationAdvisor.builder()
            .documentRetriever(
                WebSearchDocumentRetriever.builder()
                    .restClientBuilder(restClientBuilder)
                    .maxResults(5)
                    .build())
            .build();
    return chatClientBuilder
        .defaultAdvisors(
            List.of(loggerAdvisor, memoryAdvisor, tokenUsageAdvisor, webSearchRAGAdvisor))
        .build();
  }
}
