package com.example.springaiagents;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SequentialAgentTest {

  @Test
  public void testSequentialAgent() {
    SequentialAgent agent = SequentialAgent.builder()
        .name("Test Sequential Agent")
        .agents(List.of(new IncrementingAgent(), new DoublingAgent()))
        .build();

    String result = agent.run("5");
    Assertions.assertThat(result).isEqualTo("12");
  }

}
