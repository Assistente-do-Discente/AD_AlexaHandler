package br.assistentediscente.alexa.handlers.custom;

import br.assistentediscente.alexa.enums.SystemMessage;
import br.assistentediscente.alexa.requests.ApiAssistenteDiscente;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import java.util.Objects;
import java.util.Optional;

import static br.assistentediscente.alexa.Messages.getMessage;
import static br.assistentediscente.alexa.enums.SystemMessage.*;

public abstract class BaseIntentHandler {


    protected abstract String getCardTitle();


    /**
     * Responsavel por montar a resposta que será apresentada ao usuario
     * @param input informações vindas da Alexa a partir da chamada do usuario
     * @param parameters parametros recebidos pelo usuario e que serão passados para API
     * @return resposta que será apresentada ao usuario
     */
    protected Optional<Response> getResponse(HandlerInput input, String[] parameters, boolean haveParams){
        String speech = getData(parameters, getAccessToken(input), haveParams);
        return getResponsePresentation(input, speech);
    }

    protected Optional<Response> getResponseGenericService(HandlerInput input, String serviceActivationName, String[] parameters, boolean haveParams){
        String speech = callServiceAPI(serviceActivationName, parameters, getAccessToken(input), haveParams);
        return getResponsePresentation(input, speech);
    }

    protected Optional<Response> getErrorResponse(HandlerInput input, SystemMessage systemMessage){
        String speech = getMessage(systemMessage);
        return getResponsePresentation(input, speech);
    }

    private Optional<Response> getResponsePresentation(HandlerInput input, String speech) {
        return input.getResponseBuilder()
                .withSimpleCard(getCardTitle(), speech)
                .withSpeech(speech)
                .withReprompt(getMessage(REPROMPT))
                .build();
    }
    /**
     * Obtem o nome do intent a ser chamado na API a partir do nome do IntentHandler chamado pela Alexa
     * @return nome do intent a ser chamado na API
     */
    protected String getIntentName() {
        return this.getClass().getSimpleName().split("IntentHandler")[0];
    }

    /**
     * Metodo responsavel por obter o Token de conexão do servidor de autenticação do AD
     * @param input informações vindas da Alexa a partir da chamada do usuario
     * @return Token JWT do usuario
     */
    private String getAccessToken(HandlerInput input) {
        try {
            return input.getRequestEnvelope().getContext().getSystem()
                    .getUser().getAccessToken();
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * Metodo que ira fazer a chamada da API, passando o Token JWT, o nome do Intent a ser chamado na API
     * e os parametros de busca/filtro
     * @param parameters parametros recebidos pelo usuario e que serão passados para API
     * @param jwt token de conexão da skill com o servidor de autenticação do AD
     * @return frase de resposta vinda da API ou de erros de validação feito pelo Handler
     */
    private String getData(String[] parameters, String jwt, boolean haveParams) {
        try {
            if (!validateToken(jwt))
                return getMessage(ERROR_TOKEN);

            if (haveParams && !validateParams(parameters))
                return getMessage(SystemMessage.getRandomErrorParams());

            return ApiAssistenteDiscente.getResponse(jwt, getIntentName(), parameters, haveParams);
        }catch (Throwable throwable){
            return getMessage(ERROR_DEFAULT);
        }
    }

    private String callServiceAPI(String serviceActivationName, String[] parameters, String jwt, boolean haveParams) {
        try {
            if (!validateToken(jwt))
                return getMessage(ERROR_TOKEN);

            if (haveParams && !validateParams(parameters))
                return getMessage(SystemMessage.getRandomErrorParams());

            return ApiAssistenteDiscente.callService(jwt, serviceActivationName, parameters, haveParams);
        }catch (Throwable throwable){
            return getMessage(ERROR_DEFAULT);
        }
    }

    private boolean validateToken(String jwt){
        return Objects.nonNull(jwt) && !jwt.isEmpty();
    }

    private boolean validateParams(String[] parameters){
        return Objects.nonNull(parameters)
                && parameters.length > 0;
    }

    protected String getSlot(IntentRequest request, String slotName){
        if (request.getIntent().getSlots() != null && request.getIntent().getSlots().containsKey(slotName)){
            Slot slot = request.getIntent().getSlots().get(slotName);
            return slot.getValue();
        }

        return null;
    }

    protected boolean paramsFullFill(String... params){
        for(String param : params){
            if(param == null || param.isEmpty()){
                return false;
            }
        }
        return true;
    }
}
