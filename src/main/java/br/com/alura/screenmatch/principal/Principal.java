package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> dadosSerie = new ArrayList<>();
    private SerieRepository repository;
    private List<Serie> series = new ArrayList<>();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {

            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar Séries buscadas
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    buscarSeriesListadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSeriesListadas() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        repository.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        String json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        buscarSeriesListadas();
        System.out.println("Informe o nome da série que você busca:");
        String nomeSerie = leitura.nextLine();

        Optional<Serie> optionalSerie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();
        if (optionalSerie.isPresent()) {
            Serie serie = optionalSerie.get();
            List<DadosTemporada> temporadas = getTemporadasFrom(serie);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e ->
                                    new Episodio(e.numero(), e)))
                    .collect(Collectors.toList());
            serie.setEpisodios(episodios);
            repository.save(serie);
            serie.getEpisodios().sort(Comparator.comparing(Episodio::getTemporada));
            serie.getEpisodios().forEach(System.out::println);
        } else {
            System.out.println("Serie não encontrada! ");
        }
    }

    private List<DadosTemporada> getTemporadasFrom(Serie serie) {
        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= serie.getTotalTemporadas(); i++) {
            String json = consumo.obterDados(ENDERECO + serie.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        return temporadas;
    }
}