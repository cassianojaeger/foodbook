package br.ufrgs.foodbook.converter.impl;

import br.ufrgs.foodbook.converter.Converter;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class DataGenericConverter<S, T> implements Converter<S, T>
{
    private static final Logger LOGGER = Logger.getLogger(DataGenericConverter.class.getName());
    private static final String NULL_PARAM_MSG = "source or target can not be null";

    @Override
    public T convert(S source, T target)
    {
        if (isNull(source) || isNull(target)) throw new NullPointerException(NULL_PARAM_MSG);

        List<Field> convertibleFields = Arrays
            .stream(getSourceDeclaredFields(source))
            .filter(getEquivalentFields(target))
            .collect(Collectors.toList());

        convertibleFields.forEach(populateTargetFields(source, target));

        return target;
    }

    private Consumer<Field> populateTargetFields(S source, T target)
    {
        return field -> {
            field.setAccessible(true);
            try
            {
                FieldUtils.setProtectedFieldValue(field.getName(), target, FieldUtils.getFieldValue(source, field.getName()));
            } catch (IllegalAccessException e)
            {
                LOGGER.severe("deu merda na reflection hein gurizada, os campos tem que ter o mesmo nome no S e no T");
            }
        };
    }

    private Predicate<Field> getEquivalentFields(T target)
    {
        return field -> Arrays
                .stream(getTargetDeclaredFields(target))
                .anyMatch(haveFieldsSameName(field));
    }

    private Field[] getTargetDeclaredFields(T target)
    {
        return target.getClass().getDeclaredFields();
    }

    private Field[] getSourceDeclaredFields(S source)
    {
        return source.getClass().getDeclaredFields();
    }

    private Predicate<Field> haveFieldsSameName(Field field)
    {
        return targetField -> targetField.getName().equals(field.getName());
    }
}