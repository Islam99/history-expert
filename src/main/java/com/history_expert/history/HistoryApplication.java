package com.history_expert.history;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HistoryApplication {

  public static void main(String[] args) {
    System.out.println("DEBUG KEY: " + System.getenv("OPENAI_API_KEY"));
    SpringApplication.run(HistoryApplication.class, args);
  }
}
