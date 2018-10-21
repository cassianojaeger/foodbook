package br.ufrgs.foodbook.strategies.converter;

public interface Converter<S, T>
{
    T convert(S source);
}
