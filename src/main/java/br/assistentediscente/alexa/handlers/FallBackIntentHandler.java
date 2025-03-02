package br.assistentediscente.alexa.handlers;

import br.assistentediscente.alexa.enums.SystemMessage;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static br.assistentediscente.alexa.Messages.getMessage;
import static com.amazon.ask.request.Predicates.intentName;

public class FallBackIntentHandler implements RequestHandler{

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = getMessage(SystemMessage.getRandomErrorParams());
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Repita a Frase", speechText)
                .withReprompt(speechText)
                .build();
    }

}