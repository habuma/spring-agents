package com.example.springaiagents;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoopingAgentTest {

  @Test
  public void testLoopingAgent() {
    var loopingAgent = LoopingAgent.builder()
        .name("testLoopingAgent")
        .agents(new IncrementingAgent(), new DoublingAgent())
        .iterations(4)
        .build();
    var result = loopingAgent.run("1");
    Assertions.assertThat(result).isEqualTo("46");
  }
}
