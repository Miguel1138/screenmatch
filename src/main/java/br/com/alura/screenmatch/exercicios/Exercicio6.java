package br.com.alura.screenmatch.exercicios;

public enum Exercicio6 {
    JANEIRO,  FEVEREIRO, MARCO, ABRIL, MAIO, JUNHO, JULHO, AGOSTO, SETEMBRO, OUTUBRO, NOVEMBRO, DEZEMBRO;

    public int getNumeroDeDias() {
        int dias = switch (this) {
            case JANEIRO, MARCO, MAIO, JULHO, AGOSTO, OUTUBRO, DEZEMBRO -> dias = 31;
            case ABRIL, JUNHO, SETEMBRO, NOVEMBRO -> dias = 30;
            case FEVEREIRO -> dias = 28;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
        return dias;
    }
}



