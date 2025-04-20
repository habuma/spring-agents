package com.example.springaiagents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringAiAgentsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringAiAgentsApplication.class, args);
  }

}
