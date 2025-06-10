package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String titulo);

    Optional<List<Serie>> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String atores, Double avaliacao);

    Optional<List<Serie>> findTop5ByOrderByAvaliacaoDesc();

    Optional<List<Serie>> findByGenero(Categoria categoria);

    Optional<List<Serie>> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqualOrderByAvaliacaoDesc(Integer totalTemporadas, Double avaliacao);

    @Query("select s from Serie s " +
            "where  s.totalTemporadas <= :totalTemporadas " +
            "and s.avaliacao >= :avaliacao " +
            "order by s.avaliacao desc")
    Optional<List<Serie>> findTotalTemporadasByAvalicao(Integer totalTemporadas, Double avaliacao);

    @Query("select e from Serie s " +
            "join s.episodios e " +
            "where upper(e.titulo) like concat('%', upper(:titulo), '%')")
    Optional<List<Episodio>> findEpisodesByTitle(String titulo);

    @Query("select e from Serie s " +
            "join s.episodios e " +
            "where s = :serie " +
            "order by e.avaliacao desc " +
            "limit 5")
    Optional<List<Episodio>> findTop5EpisodiosBySerie(Serie serie);
}