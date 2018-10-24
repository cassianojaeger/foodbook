package br.ufrgs.foodbook.strategies.populator;

public interface Populator<S, T>
{
    static final String USER_NOT_FOUND = "Este usuário não existe";

    void populate(S source, T target);
}
