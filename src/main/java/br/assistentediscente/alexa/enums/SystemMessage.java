package br.assistentediscente.alexa.enums;
import java.util.Random;

public enum SystemMessage {

    ERROR_PARAMS("ERR-001"),
    ERROR_PARAMS2("ERR-004"),
    ERROR_PARAMS3("ERR-005"),
    ERROR_TOKEN("ERR-002"),
    ERROR_DEFAULT("ERR-003"),
    REPROMPT("MSG-001");


    private final String messageCode;
    SystemMessage(String messageCode) {
        this.messageCode = messageCode;
    }
    public String getMessageCode() {
        return messageCode;
    }

    public static SystemMessage getRandomErrorParams(){
        SystemMessage[] errors = {ERROR_PARAMS, ERROR_PARAMS2, ERROR_PARAMS3};
        Random random = new Random();
        return errors[random.nextInt(errors.length)];
    }
}
