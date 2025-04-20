package com.example.springaiagents;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This is not an actual unit or integration test, but rather just a test
 * that runs a coding scenario that exercise the LLMAgent and LoopingAgent.
 */
@SpringBootTest
public class CodingScenarioTest {

  @Autowired
  ChatClient.Builder chatClientBuilder;

  @Test
  public void codingScenario() {
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
  }

}
