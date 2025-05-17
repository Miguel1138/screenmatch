package br.com.alura.screenmatch.exercicios;

public class Exercicio3 {

    public static void main(String[] args) {
        System.out.println(obterPrimeiroEUltimoNome("  João Carlos Silva   ")); // Saída: "João Silva"
        System.out.println(obterPrimeiroEUltimoNome("Maria   ")); // Saída: "Maria"


    }

    public static String obterPrimeiroEUltimoNome(String nomeCompleto) {
        nomeCompleto = nomeCompleto.trim();
        String[] palavras = nomeCompleto.split("\\s+");
        if (palavras.length == 1) return palavras[0];

        return palavras[0] + " " + palavras[palavras.length - 1];
    }

}
