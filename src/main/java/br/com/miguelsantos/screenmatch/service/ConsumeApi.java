package br.com.miguelsantos.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumeApi {

    public String obtainData(String address) {
        // Cria o cliente
        HttpClient client = HttpClient.newHttpClient();
        // Cria a requisição web com base no endereço enviado pela assinatura do metodo
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();
        // Cria o objeto que vai pegar a resposta da requisição
        HttpResponse<String> response;


        // Efetua o processo de requisição do enereço
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Retorn um arquivo json da requisição.
        return response.body();
    }
}
