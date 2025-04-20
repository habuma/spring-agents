# Spring Agents Experiment

This repository contains the results of a rough, work-in-progress experiment
as an attempt to roughly recreate Google's Agent Development Kit (ADK)--and
similar efforts from OpenAI's Agent SDK and Pydantic AI--but built in Java
and with Spring AI at its core.

If it wasn't clear from that description, this is a work in progress and is not
intended for production use. It still has missing pieces and there is more work
to be done. But it's a good start.

## Key Components

The key components in play are:

- `LLMAgent`: A simple agent that uses a large language model (LLM) to
  generate responses to user input. It can be used for simple tasks like
  answering questions or generating text. It also accepts (via its builder)
  one or more tools.
- `LoopingAgent`: An agent that can loop over a set of sub-agents, using the
  output of one tool as the input to the next. The result from the final agent
  is fed as input to the first agent as the loop iterates. It will run through
  at most 5 iterations by default, but that can be configured through its
  builder.
- `SequentialAgent`: An agent that can run a set of sub-agents in sequence,
  using the output of one tool as the input to the next. The result from the
  final agent is returned as the result of the entire sequence.

These agents are approximations of agents of the same name in Google's ADK,
although that may not work exactly the same way.

## Spring Agents in Action

To see this in action, have a look at `src/test/java/com/example/springagents/CodingScenarioTest.java`.
Unlike the other tests in the same package, this is not an actual unit or
integration test. Instead, it simply uses the test framework to run a
simple scenario that exercises `LLMAgent`, `LoopingAgent`, and (indirectly)
`SequentialAgent`.

