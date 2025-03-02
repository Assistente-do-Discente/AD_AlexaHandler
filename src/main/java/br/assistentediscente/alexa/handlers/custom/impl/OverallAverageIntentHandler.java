package br.assistentediscente.alexa.handlers.custom.impl;

import br.assistentediscente.alexa.handlers.custom.BaseIntentHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class OverallAverageIntentHandler extends BaseIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("OverallAverageIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        return getResponse(input, new String[]{}, false);
    }

    @Override
    protected String getCardTitle() {
        return "Média Geral do Curso: ";
    }
}