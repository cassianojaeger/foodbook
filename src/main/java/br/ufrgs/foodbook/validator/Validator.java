package br.ufrgs.foodbook.validator;

public interface Validator<T>
{
    static final String NULL_FIELD_ERROR_MESSAGE = "Por favor, preencha este campo";

    void validate(T obj);
}
