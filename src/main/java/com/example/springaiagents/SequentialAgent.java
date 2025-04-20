package com.example.springaiagents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SequentialAgent implements Agent {

  private static final Logger logger = LoggerFactory.getLogger(SequentialAgent.class);

  private final String name;
  private final String description;
  private final List<Agent> agents;

  public SequentialAgent(String name, String description, List<Agent> agents) {
    this.name = name;
    this.description = description;
    this.agents = agents;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String run(String input) {
    logger.info("Running sequential agent " + name);
    String result = input;
    for (Agent agent : agents) {
      result = agent.run(result);
    }
    return result;
  }

  public static class Builder {
    private String name;
    private String description;
    private List<Agent> agents;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder agents(List<Agent> agents) {
      this.agents = agents;
      return this;
    }

    public SequentialAgent build() {
      return new SequentialAgent(name, description, agents);
    }
  }

}
