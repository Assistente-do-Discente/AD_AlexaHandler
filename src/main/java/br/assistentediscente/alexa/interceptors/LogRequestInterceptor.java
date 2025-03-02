package br.assistentediscente.alexa.interceptors;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Slot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogRequestInterceptor implements RequestInterceptor {

    static final Logger logger = LogManager.getLogger(LogRequestInterceptor.class);

    @Override
    public void process(HandlerInput input) {

        if (!(input.getRequest() instanceof IntentRequest)){
            return;
        }

        IntentRequest request = (IntentRequest) input.getRequestEnvelope().getRequest();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TIMESTAMP: ").append(input.getRequest().getTimestamp()).append("\n");
        stringBuilder.append("INTENT: ").append(request.getIntent().getName()).append("\n")
                     .append("SLOTS [ ");

        for (Slot slot : request.getIntent().getSlots().values()){
              stringBuilder.append("NAME: ").append(slot.getName())
                      .append(" VALUE: ").append(slot.getValue())
                      .append("\n");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        stringBuilder.append("]");

        logger.info(stringBuilder.toString());
    }
}