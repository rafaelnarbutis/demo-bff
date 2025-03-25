package com.br.customer_manager.service;

import com.br.customer_manager.dto.CustomerDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@Service
public class ViaCepService {
    private static final String BASE_URL = "https://viacep.com.br/ws/";

    public CustomerDTO getPostalCode(String cep) {
        if (!cep.matches("\\d{8}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP invalido. Deve conter apenas numeros");
        }

        OkHttpClient client = new OkHttpClient();

        String url = BASE_URL + cep + "/json/";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao chamar ViaCEP: " + response);
            }

            String jsonResponse = response.body().string();
            log.info("Resposta JSON do ViaCEP: {}", jsonResponse);

            Gson gson = new Gson();
            CustomerDTO address = gson.fromJson(jsonResponse, CustomerDTO.class);

            if (address.getStreet() == null || address.getCity() == null || address.getState() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP nao encontrado ou invalido.");
            }

            log.info("Endereco encontrado: {}", address);
            return address;
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar resposta do ViaCEP.", e);
        }
    }
}
