package br.com.miguelsantos.screenmatch.principal;

import br.com.miguelsantos.screenmatch.model.Seasons;
import br.com.miguelsantos.screenmatch.model.Series;
import br.com.miguelsantos.screenmatch.service.ConsumeApi;
import br.com.miguelsantos.screenmatch.service.ConvertData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    private Scanner scanner = new Scanner(System.in);
    private ConvertData converter = new ConvertData();
    private List<Seasons> seasons = new ArrayList<>();
    private ConsumeApi api = new ConsumeApi();

    public void showMenu() {
        System.out.println("Informe o nome da serie que voce busca: ");
        String name = scanner.nextLine().toLowerCase().replace(" ", "+");

        var json = api.obtainData(ADDRESS + name + API_KEY);
        Series dataSeries = converter.convertData(json, Series.class);
        System.out.println(dataSeries);

        // TODO tratar exceção de nome mal escrito.
        for (int i = 1; i <= dataSeries.totalSeasons(); i++) {
            json = api.obtainData(ADDRESS + name + "&season=" + i + API_KEY);
            Seasons dataSeason = converter.convertData(json, Seasons.class);
            seasons.add(dataSeason);
        }

        seasons.forEach(System.out::println);
    }

}
