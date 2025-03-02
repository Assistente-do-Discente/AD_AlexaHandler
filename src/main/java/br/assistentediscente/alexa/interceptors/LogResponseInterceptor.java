package br.assistentediscente.alexa.interceptors;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.Card;
import com.amazon.ask.model.ui.SimpleCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LogResponseInterceptor implements ResponseInterceptor {

    static final Logger logger = LogManager.getLogger(LogResponseInterceptor.class);

    @Override
    public void process(HandlerInput input, Optional<Response> output) {
        StringBuilder stringBuilder = new StringBuilder();
        if (output.isPresent()) {
            Card card = output.get().getCard();
            if (card instanceof SimpleCard simpleCard){
                stringBuilder.append("RESPONSE TITLE: ").append(simpleCard.getTitle()).append("\n")
                        .append("RESPONSE TEXT: ").append(simpleCard.getContent());
                logger.info(stringBuilder.toString());
                return;
            }
        }

        logger.info(output.toString());
    }
}