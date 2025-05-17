package br.com.alura.screenmatch.exercicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercício1 {

    public static void main(String[] args) {
        List<String> input = Arrays.asList("10", "abc", "20", "30x");
        List<Integer> inteiros = new ArrayList<>();

        input.stream().forEach(v -> {
            try {
                int i = Integer.parseInt(v);
                inteiros.add(i);
            } catch (NumberFormatException e) {

            }
        });

        inteiros.forEach(System.out::println);
    }


}
