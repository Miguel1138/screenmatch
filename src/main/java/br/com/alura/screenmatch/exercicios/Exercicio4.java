package br.com.alura.screenmatch.exercicios;

public class Exercicio4 {

    public static void main(String[] args) {
        System.out.println(ehPalindromo("subi no onibus em marrocos")); // Saída: true
        System.out.println(ehPalindromo("Java")); // Saída: false
        System.out.println(ehPalindromo("Java")); // Saída: false



    }

    public static boolean ehPalindromo(String palavra) {
        return palavra.equals(new StringBuilder(palavra).reverse().toString());
    }

}
