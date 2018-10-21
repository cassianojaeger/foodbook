package br.ufrgs.foodbook.strategies.populator;

public interface Populator<S, T>
{
    void populate(S source, T target);
}
