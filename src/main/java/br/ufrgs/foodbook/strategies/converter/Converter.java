package br.ufrgs.foodbook.strategies.converter;

import java.util.Set;

public interface Converter<S, T>
{
    T convert(S source);

    Set<T> convertAll(Set<S> sources);
}
