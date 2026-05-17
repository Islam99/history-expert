package com.history_expert.history.controller;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HistoryController {

  private ChatClient chatClient;

  private ChatClient webSearchChatClient;

  private ChatClient vectorStoreChatClient;

  public HistoryController(
      ChatClient chatClient,
      @Qualifier("webSearchRAGChatClient") ChatClient webSearchChatClient,
      @Qualifier("vectorStoreChatClient") ChatClient vectorStoreChatClient) {
    this.chatClient = chatClient;
    this.webSearchChatClient = webSearchChatClient;
    this.vectorStoreChatClient = vectorStoreChatClient;
  }

  // getHistory with chatMemory
  @GetMapping("/history")
  public ResponseEntity<String> getHistoryData(
      @RequestHeader("username") String username, @RequestParam("message") String message) {
    return ResponseEntity.ok(
        chatClient
            .prompt()
            .user(message)
            .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
            .call()
            .content());
  }

  @GetMapping("/history/web-search")
  public ResponseEntity<String> getHistoryDataWebSearch(
      @RequestHeader("username") String username, @RequestParam("message") String message) {
    return ResponseEntity.ok(
        webSearchChatClient
            .prompt()
            .user(message)
            .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
            .call()
            .content());
  }

  @GetMapping("/history/document")
  public ResponseEntity<String> getHistoryDocument(
      @RequestHeader("username") String username, @RequestParam("message") String message) {
    return ResponseEntity.ok(
        vectorStoreChatClient
            .prompt()
            .user(message)
            .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
            .call()
            .content());
  }
}
