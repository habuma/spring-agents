package com.example.springaiagents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;

import java.util.List;

public class LLMAgent implements Agent {

  private static final Logger logger = LoggerFactory.getLogger(LLMAgent.class);

  private final String name;
  private final String description;
  private final List<String> instructions;
  private final List<String> tools;
  private final ChatClient chatClient;

  public LLMAgent(
      ChatClient.Builder chatClientBuilder,
      String name,
      String description,
      List<String> instructions,
      List<String> tools,
      List<Advisor> advisors) {
    this.name = name;
    this.description = description;
    this.instructions = instructions;
    this.tools = tools;

    this.chatClient = chatClientBuilder
        .defaultSystem(String.join("\n", instructions))
        .defaultTools(tools)
        .defaultAdvisors(advisors)
        .build();
  }

  public String run(String input) {
    logger.info("Running LLM agent " + name);
    return chatClient.prompt(input)
        .call()
        .content();
  }

  // TODO : Run async

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getInstructions() {
    return instructions;
  }

  public List<String> getTools() {
    return tools;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String name;
    private String description;
    private List<String> instructions;
    private List<String> tools;
    private List<Advisor> advisors;
    private ChatClient.Builder chatClientBuilder;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder instructions(String... instructions) {
      this.instructions = List.of(instructions);
      return this;
    }

    public Builder tools(List<String> tools) {
      this.tools = tools;
      return this;
    }

    public Builder advisors(List<Advisor> advisors) {
      this.advisors = advisors;
      return this;
    }

    public Builder chatClientBuilder(ChatClient.Builder chatClientBuilder) {
      this.chatClientBuilder = chatClientBuilder;
      return this;
    }

    public LLMAgent build() {
      if (name == null || name.isEmpty()) {
        throw new IllegalArgumentException("Name cannot be null or empty");
      }
      if (description == null || description.isEmpty()) {
        this.description = "";
      }
      if (instructions == null || instructions.isEmpty()) {
        throw new IllegalArgumentException("Instructions cannot be null or empty");
      }
      if (tools == null || tools.isEmpty()) {
        this.tools = List.of();
      }
      if (advisors == null || advisors.isEmpty()) {
        this.advisors = List.of();
      }

      return new LLMAgent(chatClientBuilder, name, description, instructions, tools, advisors);
    }
  }

}
