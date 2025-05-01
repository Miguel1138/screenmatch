package br.com.miguelsantos.screenmatch.model;

public enum FilmCategory {
    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime"),
    FANTASIA("Fantasy"),
    HORROR("Horror"),
    THRILLER("Mystery"),
    AVENTURA("Adventure"),
    FICCAO_CIENTIFICA("Sci-Fi");

    private String categoryOMB;

    FilmCategory(String categoryOMB) {
        this.categoryOMB = categoryOMB;
    }

    public static FilmCategory fromString(String text) {
        for (FilmCategory category : FilmCategory.values()) {
            if (category.categoryOMB.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Nenhuma Categoria encontrada para a string fornecida: " + text);
    }
}
