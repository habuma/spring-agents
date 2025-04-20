package com.example.springaiagents;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.io.IOException;
import java.nio.charset.Charset;

@EnableWireMock(@ConfigureWireMock(baseUrlProperties = "openai.base.url"))
@SpringBootTest(properties = "spring.ai.openai.base-url=${openai.base.url}")
public class LLMAgentTest {

  @Value("classpath:/test-openai-response.json")
  Resource responseResource;

  @Autowired
  ChatClient.Builder chatClientBuilder;

  @BeforeEach
  public void setup() throws IOException {
    var cannedResponse = responseResource.getContentAsString(Charset.defaultCharset());
    ObjectMapper mapper = new ObjectMapper();
    JsonNode responseNode = mapper.readTree(cannedResponse);
    WireMock.stubFor(WireMock.post("/v1/chat/completions")
            .willReturn(ResponseDefinitionBuilder.okForJson(responseNode)));
  }

  @Test
  public void testLLMAgent() {
    LLMAgent llmAgent = LLMAgent.builder()
        .name("Test LLM Agent")
        .instructions("Tell a joke")
        .chatClientBuilder(chatClientBuilder)
        .build();
    var result = llmAgent.run("Tell me a joke");
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isEqualTo(
        "Why did the monkey fall out of the tree? Because it was dead!");
  }

}
