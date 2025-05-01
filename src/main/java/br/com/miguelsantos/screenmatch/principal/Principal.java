package br.com.miguelsantos.screenmatch.principal;

import br.com.miguelsantos.screenmatch.model.Seasons;
import br.com.miguelsantos.screenmatch.model.DataSeries;
import br.com.miguelsantos.screenmatch.model.Serie;
import br.com.miguelsantos.screenmatch.service.ConsumeApi;
import br.com.miguelsantos.screenmatch.service.ConvertData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<DataSeries> dataSeriesList = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);
    private ConvertData converter = new ConvertData();
    private List<Seasons> seasons = new ArrayList<>();
    private ConsumeApi api = new ConsumeApi();

    public void showMenu() {
        int option = -1;
        while (option != 0) {
            String menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    searchWebSerie();
                    break;
                case 2:
                    searchEpisodeBySerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void searchEpisodeBySerie() {
        DataSeries series = getSeriesData();
        List<Seasons> seasonsList = new ArrayList<>();

        for (int i = 1; i <= series.totalSeasons(); i++) {
            String json = api.obtainData(ADDRESS + series.title().replace(" ", "+") + "&season=" + i + API_KEY);
            Seasons season = converter.convertData(json, Seasons.class);
            seasonsList.add(season);
        }
        seasonsList.forEach(System.out::println);
    }

    private void searchWebSerie() {
        DataSeries data = getSeriesData();
        dataSeriesList.add(data);
        System.out.println(data);
    }

    private DataSeries getSeriesData() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = scanner.nextLine();
        var json = api.obtainData(ADDRESS + nomeSerie.replace(" ", "+") + API_KEY);
        DataSeries series = converter.convertData(json, DataSeries.class);
        return series;
    }

    private void listarSeriesBuscadas() {
        List<Serie> series = new ArrayList<>();
        series = dataSeriesList.stream()
                .map(d -> new Serie(d))
                .collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }

}
