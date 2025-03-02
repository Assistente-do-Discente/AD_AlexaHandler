package br.assistentediscente.alexa.handlers.custom.impl;

import br.assistentediscente.alexa.enums.SystemMessage;
import br.assistentediscente.alexa.handlers.custom.BaseIntentHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GenericServicesIntentHandler extends BaseIntentHandler implements RequestHandler {
    @Override
    protected String getCardTitle() {
        return "Serviço";
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GenericServicesIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        String serviceActivationName = getSlot( (IntentRequest) input.getRequest(),
                "serviceActivationName" );

        if (!paramsFullFill(serviceActivationName)) {
            return getErrorResponse(input, SystemMessage.getRandomErrorParams());
        }
        serviceActivationName = serviceActivationName.replace(" ", "-");

        return getResponseGenericService(input, serviceActivationName, new String[]{}, false);
    }
}
