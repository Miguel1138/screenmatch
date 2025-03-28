package br.com.miguelsantos.screenmatch.service;

public interface IConvertData {
    // Uso de  Generics pois não sanemos qual o tipo de classe que será retornado.
    <T> T convertData(String json, Class<T> clazz);
}
