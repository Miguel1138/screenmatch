package br.com.alura.screenmatch.model;

public enum Categoria {
    ROMANCE("Romance", "Romance"),
    DRAMA("Drama", "Drama"),
    AVENTURA("Adventure", "Aventura"),
    COMEDIA("Comedy", "Comédia"),
    CRIME("Crime", "Crime"),
    ACAO("Action", "Ação");

    private String categoriaOmdb;
    private String categoriaPtBr;

    Categoria(String categoriaOmdb, String categoriaPtBr) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPtBr = categoriaPtBr;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromStringPtBr(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPtBr.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
