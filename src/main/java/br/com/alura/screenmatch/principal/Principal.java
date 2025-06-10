package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
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
                    4 - buscar por nome
                    5 - Buscar por nome do ator
                    6 - Buscar 5 melhores notas
                    7 - Buscar série por categoria
                    8 - Buscar serie por total de temporadas e avaliação
                    9 - Buscar episodios pelo nome
                    10 - Buscar top 5 episodios por érie
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
                case 4:
                    buscarSeriePorNome();
                    break;
                case 5:
                    buscarPorNomeAtor();
                    break;
                case 6:
                    findTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    buscarSeriesPorTotalTemporadasEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTitulo();
                    break;
                case 10:
                    buscarTopEpisodiosPorSerie();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarTopEpisodiosPorSerie() {
        System.out.println("Digite o nome da série:");
        String nomeSerie = leitura.nextLine();
        Optional<Serie> optionalSerie = repository.findByTituloContainingIgnoreCase(nomeSerie);
        if (optionalSerie.isPresent()) {
            Serie serie = optionalSerie.get();
            Optional<List<Episodio>> topEpisodios = repository.findTop5EpisodiosBySerie(serie);
            topEpisodios.ifPresent(episodios -> episodios.forEach(e ->
                    System.out.printf("Série: %s Temporada %s - Episódio %s - %s Avaliacao - %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao())));

        } else
            System.out.println("teste");
    }

    private void buscarEpisodioPorTitulo() {
        System.out.println("Qual o nome do episódio para busca?");
        String trechoEpisodio = leitura.nextLine();
        Optional<List<Episodio>> episodiosEncontrados = repository.findEpisodesByTitle(trechoEpisodio);
        episodiosEncontrados.ifPresent(episodios ->
                episodios.forEach(e -> System.out.printf("Série: %s Temporada %s - Episódio %s - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo())));
    }

    private void buscarSeriesPorTotalTemporadasEAvaliacao() {
        System.out.println("Informe a quanitdade máxima que a serie deve ter");
        Integer maxTemporadas = leitura.nextInt();
        System.out.println("Agora informe a avaliação mínima que você busca");
        Double avaliacaoMinima = leitura.nextDouble();

        Optional<List<Serie>> seriesEncontradas =
                repository.findTotalTemporadasByAvalicao(maxTemporadas, avaliacaoMinima);

        seriesEncontradas.ifPresent(serieList -> {
            serieList.forEach(System.out::println);
        });
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Informe o tipo de categoria que você busca");
        String nomeGenereo = leitura.nextLine();
        Categoria categoria = Categoria.fromStringPtBr(nomeGenereo);
        Optional<List<Serie>> seriesEncontradas = repository.findByGenero(categoria);
        seriesEncontradas.ifPresent(serieList -> serieList.forEach(System.out::println));
    }

    private void findTop5Series() {
        Optional<List<Serie>> topSeries = repository.findTop5ByOrderByAvaliacaoDesc();
        topSeries.ifPresent(
                serieList -> serieList.forEach(
                        s -> System.out.println("Nome: " + s.getTitulo() + " - Nota: " + s.getAvaliacao())));
    }

    private void buscarSeriePorNome() {
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = leitura.nextLine();
        Optional<Serie> serie = repository.findByTituloContainingIgnoreCase(nomeSerie);
        if (serie.isPresent()) {
            System.out.println(serie.get());
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void buscarPorNomeAtor() {
        System.out.println("Digite o nome da série para busca");
        String nomeAtor = leitura.nextLine();
        System.out.println("QUal a nota mínima para busca?");
        Double notaMinima = leitura.nextDouble();
        Optional<List<Serie>> seriesComAtorBuscado
                = repository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, notaMinima);
        if (seriesComAtorBuscado.isPresent()) {
            System.out.println("Series em que " + nomeAtor + " participou com nota maior ou igual a " + notaMinima);
            seriesComAtorBuscado.get()
                    .forEach(s -> System.out.println("Nome:" + s.getTitulo() + " - Nota: " + s.getAvaliacao()));
        } else {
            System.out.println("Nenhuma série com esses critérios foi encontrado.");
        }
    }

    private void showList(List<Serie> series) {
        series.forEach(s ->
                System.out.println());
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

    // TODO este método está colocando o vlaor errado nas temporadas
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