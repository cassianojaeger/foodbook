package br.ufrgs.foodbook.converter;

public interface Converter<S, T>
{
    T convert(S source, T target);
}
