package com.example.springaiagents;

public class IncrementingAgent implements Agent {
  public String run(String input) {
    return String.valueOf(Long.valueOf(input) + 1);
  }
}
