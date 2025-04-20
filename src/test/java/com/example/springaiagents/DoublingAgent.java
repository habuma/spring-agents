package com.example.springaiagents;

public class DoublingAgent implements Agent {
  public String run(String input) {
    return String.valueOf(Long.valueOf(input) * 2);
  }
}
