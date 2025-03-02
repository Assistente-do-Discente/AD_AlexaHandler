package br.assistentediscente.alexa.handlers.custom.impl;

import br.assistentediscente.alexa.enums.SystemMessage;
import br.assistentediscente.alexa.handlers.custom.BaseIntentHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ScheduleByDayIntentHandler extends BaseIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ScheduleByDayIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        String date = getSlot((IntentRequest) input.getRequest(), "date");

        if (!paramsFullFill(date)){
            return getErrorResponse(input,SystemMessage.getRandomErrorParams());
        }

        return getResponse(input, new String[]{date}, true);
    }

    @Override
    protected String getCardTitle() {
        return "Aulas: ";
    }
}