package br.assistentediscente.alexa.handlers.custom.impl;

import br.assistentediscente.alexa.enums.SystemMessage;
import br.assistentediscente.alexa.handlers.custom.BaseIntentHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GradeBySemesterIntentHandler extends BaseIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GradeBySemesterIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        String semester = getSlot( (IntentRequest) input.getRequest(), "semester");

        if (!paramsFullFill(semester)) {
            return getErrorResponse(input,SystemMessage.getRandomErrorParams());
        }

        String semestreMounted = mountSemester(semester);

        if (semestreMounted.length() != 6) {
            return getErrorResponse(input,SystemMessage.getRandomErrorParams());
        }

        return getResponse(input, new String[]{semestreMounted}, true);
    }

    private static String mountSemester(String semester) {
        String cleanedSemester = semester.replace(" ", "");
        StringBuilder stringBuilder = new StringBuilder(cleanedSemester);
        stringBuilder.insert(4, '/');
        return stringBuilder.toString();
    }

    @Override
    protected String getCardTitle() {
        return "Notas do Semestre";
    }
}
