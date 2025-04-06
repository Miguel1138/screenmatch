package br.com.miguelsantos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Seasons(
        @JsonAlias("Season") Integer number,
        @JsonAlias("Episodes") List<DataEpisode> episodes
) {
}
