package br.com.miguelsantos.screenmatch.model;

import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Integer totalSeasons;
    private Double imdbRating;
    private String plot;
    private FilmCategory genre;
    private String actors;
    private String poster;


    public Serie(DataSeries dataSeries) {
        this.title = dataSeries.title();
        this.totalSeasons = dataSeries.totalSeasons();
        this.imdbRating = OptionalDouble.of(Double.valueOf(dataSeries.imdbRating())).orElse(0.0);
        this.plot = dataSeries.plot();
        this.genre = FilmCategory.fromString(dataSeries.genre().split(",")[0].trim());
        this.actors = dataSeries.actors();
        this.poster = dataSeries.poster();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public FilmCategory getGenre() {
        return genre;
    }

    public void setGenre(FilmCategory genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return  "title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", imdbRating=" + imdbRating +
                ", plot='" + plot + '\'' +
                ", genre=" + genre +
                ", actors='" + actors + '\'' +
                ", poster='" + poster + '\'';
    }
}
