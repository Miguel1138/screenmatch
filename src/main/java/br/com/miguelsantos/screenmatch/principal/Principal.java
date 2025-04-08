package br.com.miguelsantos.screenmatch.principal;

import br.com.miguelsantos.screenmatch.model.Episode;
import br.com.miguelsantos.screenmatch.model.Seasons;
import br.com.miguelsantos.screenmatch.model.Series;
import br.com.miguelsantos.screenmatch.service.ConsumeApi;
import br.com.miguelsantos.screenmatch.service.ConvertData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        for (int i = 1; i <= dataSeries.totalSeasons(); i++) {
            json = api.obtainData(ADDRESS + name + "&season=" + i + API_KEY);
            Seasons dataSeason = converter.convertData(json, Seasons.class);
            seasons.add(dataSeason);
        }

        // Give the 5 top episodes
        List<Episode> episodes = seasons.stream()
                .flatMap(s ->
                        s.episodes().stream()
                                .map(data -> new Episode(s.number(), data))
                ).collect(Collectors.toList());

        System.out.println("Top 5 episodios:\n");
        episodes.stream()
                .sorted(Comparator.comparing(Episode::getRating).reversed())
                .limit(5)
                .forEach(System.out::println);

//         Filtrar por ano
        System.out.println("A partir de qual ano gostaria de ver os episodios");
        var year = scanner.nextInt();
        scanner.nextLine();
        LocalDate searchDate = LocalDate.of(year, 1, 1);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodes.stream()
                .filter(e -> e.getDataReleased() != null && e.getDataReleased().isAfter(searchDate))
                .forEach(e -> {
                            System.out.println("Temporada: " + e.getSeasonNumber()
                                    + "\n Episodio: " + e.getTitle()
                                    + "\n Data: " + e.getDataReleased().format(dateFormatter));
                        }
                );

        DoubleSummaryStatistics est = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect((Collectors.summarizingDouble(Episode::getRating)));
        System.out.println("Total episodes: " + est.getCount());
        System.out.println("Average rate: " + est.getAverage());
        System.out.println("Max Value: " + est.getMax());
        System.out.println("Minimal value: " + est.getMin());
    }

}
