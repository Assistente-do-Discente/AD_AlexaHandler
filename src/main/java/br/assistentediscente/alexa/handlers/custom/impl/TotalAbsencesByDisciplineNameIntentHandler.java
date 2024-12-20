package br.assistentediscente.alexa.handlers.custom.impl;

import br.assistentediscente.alexa.enums.SystemMessage;
import br.assistentediscente.alexa.handlers.custom.BaseIntentHandler;
import br.assistentediscente.alexa.requests.ApiAssistenteDiscente;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class TotalAbsencesByDisciplineNameIntentHandler extends BaseIntentHandler implements RequestHandler {

    public TotalAbsencesByDisciplineNameIntentHandler(ApiAssistenteDiscente apiAssistenteDiscente) {
        super(apiAssistenteDiscente);
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("TotalAbsencesByDisciplineNameIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        String disciplineName = getSlot((IntentRequest) input.getRequest(),"disciplineName");

        if(!paramsFullFill(disciplineName)){
            return getErrorResponse(input, SystemMessage.getRandomErrorParams());
        }

        return getResponse(input, new String[]{disciplineName}, true);
    }

    @Override
    protected String getCardTitle() {
        return "Quantidade de faltas";
    }
}