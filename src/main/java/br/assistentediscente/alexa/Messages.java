package br.assistentediscente.alexa;

import br.assistentediscente.alexa.enums.SystemMessage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    private static final ResourceBundle messages =
            ResourceBundle.getBundle("messages", Locale.getDefault());

    public static String getMessage(SystemMessage systemMessage) {
        return messages.getString(systemMessage.getMessageCode());
    }
}
