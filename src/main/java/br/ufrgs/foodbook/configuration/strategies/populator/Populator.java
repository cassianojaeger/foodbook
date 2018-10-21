package br.ufrgs.foodbook.configuration.strategies.populator;

public interface Populator<S, T>
{
    void populate(S source, T target);
}
