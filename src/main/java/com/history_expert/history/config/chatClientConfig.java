package com.history_expert.history.config;

import com.history_expert.history.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class chatClientConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatCLientBuilder){
        ChatMemory chatMemory = MessageWindowChatMemory.builder().build();
        ChatOptions options = ChatOptions.builder().model("gpt-4.1-mini").maxTokens(100).temperature(0.8).build();
        return chatCLientBuilder
                .defaultOptions(options)
                .defaultAdvisors(List.of(MessageChatMemoryAdvisor.builder(chatMemory).build(),new SimpleLoggerAdvisor(),new TokenUsageAuditAdvisor()))
                .defaultSystem("""
                You are an history expert. You only reply to history related questtion like dates. who invented what.
                Details about historical events like battles or what not. You don't reply to any other question.
                If you recieve one you just appologize politely.""")
                .defaultUser("Hello how can you help me ?")
                .build();
    }
}
