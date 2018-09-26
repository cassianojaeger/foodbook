package br.ufrgs.foodbook.validator;

public interface Validator<T>
{
    void validate(T obj);
}
