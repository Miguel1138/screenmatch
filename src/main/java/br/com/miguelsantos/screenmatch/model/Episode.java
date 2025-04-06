package br.com.miguelsantos.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer seasonNumber;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate dataReleased;

    public Episode(Integer seasonNumber, DataEpisode data) {
        this.seasonNumber = seasonNumber;
        this.title = data.title();
        this.episodeNumber = data.number();
        try {
            this.rating = Double.valueOf(data.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }
        try {
            this.dataReleased = LocalDate.parse(data.dataReleased());
        } catch (DateTimeParseException e) {
            this.dataReleased = null;
        }
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getDataReleased() {
        return dataReleased;
    }

    public void setDataReleased(LocalDate dataReleased) {
        this.dataReleased = dataReleased;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "seasonNumber=" + seasonNumber +
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", rating=" + rating +
                ", dataReleased=" + dataReleased +
                '}';
    }

}
