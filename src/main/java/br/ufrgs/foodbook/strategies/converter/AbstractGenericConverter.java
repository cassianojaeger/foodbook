package br.ufrgs.foodbook.strategies.converter;

import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.security.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public abstract class AbstractGenericConverter<S, T> implements Converter<S, T>
{
    private static final Logger LOGGER = Logger.getLogger(AbstractGenericConverter.class.getName());
    private static final String NULL_PARAM_MSG = "source or target can not be null";
    private final Class<T> targetClass;

    public AbstractGenericConverter(Class<T> targetClass)
    {
        this.targetClass = targetClass;
    }

    @Override
    public T convert(S source)
    {
        T target = getInstance();

        if (isNull(source) || isNull(target)) throw new NullPointerException(NULL_PARAM_MSG);

        List<Field> convertibleFields = Arrays
            .stream(getSourceDeclaredFields(source))
            .filter(getEquivalentFields(target))
            .collect(Collectors.toList());

        of(convertibleFields)
                .ifPresent(runPopulateTargetFields(source, target));

        ofNullable(getPopulators())
                .ifPresent(runPopulators(source, target));

        return target;
    }

    protected abstract List<Populator<S, T>> getPopulators();

    private Consumer<List<Field>> runPopulateTargetFields(S source, T target)
    {
        return p -> p.forEach(populateTargetFields(source, target));
    }

    private Consumer<List<Populator<S, T>>> runPopulators(S source, T target)
    {
        return p -> p.forEach(populate(source, target));
    }

    private Consumer<Populator<S, T>> populate(S source, T target)
    {
        return populator -> populator.populate(source, target);
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
                LOGGER.severe("deu merda na reflection hein gurizada, os campos tem que ter o mesmo nome no S e no T e o mesmo tipo");
            }
        };
    }

    private Predicate<Field> getEquivalentFields(T target)
    {
        return field -> Arrays
                .stream(getTargetDeclaredFields(target))
                .anyMatch(areFieldsEquivalent(field));
    }

    private T getInstance()
    {
        try {
            return targetClass.newInstance();
        }catch (IllegalAccessException | InstantiationException e)
        {
            throw new RuntimeException();
        }
    }

    private Field[] getTargetDeclaredFields(T target)
    {
        return target.getClass().getDeclaredFields();
    }

    private Field[] getSourceDeclaredFields(S source)
    {
        return source.getClass().getDeclaredFields();
    }

    private Predicate<Field> areFieldsEquivalent(Field field)
    {
        return targetField -> targetField.getName().equals(field.getName()) && targetField.getType().equals(field.getType());
    }
}