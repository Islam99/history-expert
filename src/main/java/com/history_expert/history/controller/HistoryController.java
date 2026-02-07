package com.history_expert.history.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
public class HistoryController {
    private ChatClient chatClient;

    public HistoryController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/history")
    public ResponseEntity<String> getHistoryData(@RequestHeader("username") String username,
                                                 @RequestParam("message") String message){
        return ResponseEntity.ok(chatClient.prompt().user(message)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,username)).call().content());
    }
}
