package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String titulo);

    Optional<List<Serie>> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String atores, Double avaliacao);

    Optional<List<Serie>> findTop5ByOrderByAvaliacaoDesc();
}
