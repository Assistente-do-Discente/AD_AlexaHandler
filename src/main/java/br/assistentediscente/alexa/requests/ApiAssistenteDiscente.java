package br.assistentediscente.alexa.requests;

import br.assistentediscente.alexa.enums.SystemMessage;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static br.assistentediscente.alexa.Messages.getMessage;

public class ApiAssistenteDiscente {

    private final static Gson gson = new Gson();
    /**
     * Responsavel por fazer a chamada da API AD e trazer a resposta
     * @param jwt Token de conexão do usuario
     * @param intentName metodo que será chamado dentro da API
     * @param parameters parametros passados pelo usuario que serão usados para busca/filtro dentro da API
     * @return Resposta da API
     */
    public static String getResponse(String jwt, String intentName ,String[] parameters, boolean haveParams){
        try {
            String requestBody = buildRequestBody(parameters);
            if (haveParams && requestBody.equalsIgnoreCase("[]"))
                return getMessage(SystemMessage.ERROR_PARAMS);

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://uegenio.app.guiliano.com.br/api/make-response/"+intentName))
                    .headers("Authorization","Bearer "+jwt,
                            "Content-Type","application/json",
                            "Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers
                            .ofString(requestBody))
                    .build();
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> httpResponse =
                    httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 401) {
                return getMessage(SystemMessage.ERROR_TOKEN);
            }
            return getResponse(httpResponse);
        } catch (Throwable error) {
            error.printStackTrace();
            return getMessage(SystemMessage.ERROR_DEFAULT);
        }
    }

    private static String getResponse(HttpResponse<String> httpResponse) {
        if (httpResponse.statusCode() == 200) {
            return gson.fromJson(httpResponse.body(), ApiOkResponse.class).response();
        }
        ApiErrorResponse errorResponse= gson.fromJson(httpResponse.body(), ApiErrorResponse.class);
        return errorResponse.message();

    }

    /**
     * Responsavel por montar o corpo da requisição a ser enviada para API
     * @param parameters  parametros passados pelo usuario que serão usados para busca/filtro dentro da API
     * @return corpo do request com os parametros passados pelo usuario em formato json
     */
    private static String buildRequestBody(String[] parameters) {
        if (parameters == null || parameters.length == 0) return "[]";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (String parameter : parameters) {
            stringBuilder.append("\"").append(parameter).append("\",");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String callService(String jwt, String serviceActivationName, String[] parameters, boolean haveParams) {
        try {
            String requestBody = "{}"; //por enquanto apenas serviços sem passar parametros

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://uegenio.app.guiliano.com.br/api/do-service/"+serviceActivationName))
                    .headers("Authorization","Bearer "+jwt,
                            "Content-Type","application/json",
                            "Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers
                            .ofString(requestBody))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> httpResponse =
                    httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 401) {
                return getMessage(SystemMessage.ERROR_TOKEN);
            }
            return getResponse(httpResponse);
        } catch (Throwable error) {
            error.printStackTrace();
            return getMessage(SystemMessage.ERROR_DEFAULT);
        }
    }
}
