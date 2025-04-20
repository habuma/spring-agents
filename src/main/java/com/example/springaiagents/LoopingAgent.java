package com.example.springaiagents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoopingAgent implements Agent {

  private static final Logger logger = LoggerFactory.getLogger(LoopingAgent.class);

  private final String name;
  private final String description;
  private final Agent agent;
  private final int iterations;

  public LoopingAgent(String name, String description, int iterations, Agent agent) {
    this.name = name;
    this.description = description;
    this.agent = agent;
    this.iterations = iterations;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Agent getAgent() {
    return agent;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String run(String input) {
    logger.info("Running looping agent " + name);
    String result = input;
    for (int i = 0; i < iterations; i++) {
      logger.info(name + " : Iteration " + (i + 1) + " of " + iterations);
      result = agent.run(result);
      if (result == null || result.isEmpty()) { // check for success before iterations are met
        break;
      }
    }
    return result;
  }

  public static class Builder {
    private static final int MAX_ITERATIONS = 5;

    private String name;
    private String description;
    private Agent agent;
    private int iterations = MAX_ITERATIONS;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder agent(Agent agent) {
      this.agent = agent;
      return this;
    }

    public Builder iterations(int iterations) {
      if (iterations <= 0) {
        throw new IllegalArgumentException("Iterations must be greater than 0");
      }
      this.iterations = iterations;
      return this;
    }

    public Builder agents(Agent... agents) {
      this.agent = new SequentialAgent.Builder()
          .name(name + "-sequential")
          .description(description + " - sequential")
          .agents(List.of(agents))
          .build();
      return this;
    }

    public LoopingAgent build() {
      return new LoopingAgent(name, description, iterations, agent);
    }
  }

}
