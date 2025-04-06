package br.com.miguelsantos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.crypto.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisode(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer number,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String dataReleased
) {
}