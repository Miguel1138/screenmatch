package br.com.alura.screenmatch.exercicios;

import java.util.Optional;

public class Exercício2 {

    public static void main(String[] args) {
        System.out.println(processaNumero(Optional.of(5))); // Saída: Optional[25]
        System.out.println(processaNumero(Optional.of(-3))); // Saída: Optional.empty
        System.out.println(processaNumero(Optional.empty())); // Saída: Optional.empty


    }

    public static Optional<Integer> processaNumero(Optional<Integer> numero) {
        return numero.isPresent() && numero.get() > 0
                ? Optional.of(numero.get() * numero.get())
                : Optional.empty();
    }
}
