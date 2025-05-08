package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.DadosTraducao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;

public class MyMemoryAPI {

    public static String obterTraducao(String texto) {
        ObjectMapper mapper = new ObjectMapper();
        ConsumoApi consumoApi = new ConsumoApi();

        String translateText = URLEncoder.encode(texto);
        String langPair = URLEncoder.encode("en|pt-br");
        String url = "https://api.mymemory.translated.net/get?q=" + translateText + "&langpair=" + langPair;
        String json = consumoApi.obterDados(url);

        DadosTraducao traducao;
        try {
            traducao = mapper.readValue(json, DadosTraducao.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return traducao.getTranslation();
    }

}
