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

//  @Bean
  public ApplicationRunner go(ChatClient.Builder chatClientBuilder) {
    return args -> {
      var codingAgent = LLMAgent.builder()
          .name("coding-agent")
          .instructions("You are a Java coding assistant and will write Java code for the user per their specifications.")
          .chatClientBuilder(chatClientBuilder).build();
      var reviewAgent = LLMAgent.builder()
          .name("review-agent")
          .instructions("You are a code review assistant and will review Java code, providing suggestions for improvement.")
          .chatClientBuilder(chatClientBuilder).build();
      var loopingAgent = LoopingAgent.builder()
          .name("code-looping-agent")
          .agents(codingAgent, reviewAgent)
          .build();
      var results = loopingAgent.run("Write a Java method to translate English to Pig-Latin.");
      System.out.println("Results: \n=========\n\n" + results);
    };
  }

}
