package br.assistentediscente.alexa.requests;

public record ApiErrorResponse(
        String message,
        String code,
        String status) {
}
