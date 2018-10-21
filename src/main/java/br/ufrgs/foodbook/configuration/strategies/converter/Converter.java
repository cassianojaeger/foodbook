package br.ufrgs.foodbook.configuration.strategies.converter;

public interface Converter<S, T>
{
    T convert(S source);
}
