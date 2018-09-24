package br.ufrgs.foodbook;

import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class DataGenericConverter<SOURCE, TARGET>
{
    private static final Logger LOGGER = Logger.getLogger(DataGenericConverter.class.getName());

    public TARGET convert(SOURCE source, TARGET target)
    {
        List<Field> convertableFields = Arrays
            .stream(getSourceDeclaredFields(source))
            .filter(getEquivalentFields(target))
            .collect(Collectors.toList());

        convertableFields.forEach(populateTargetFields(source, target));

        return target;
    }

    private Consumer<Field> populateTargetFields(SOURCE source, TARGET target)
    {
        return field -> {
            field.setAccessible(true);
            try
            {
                FieldUtils.setProtectedFieldValue(field.getName(), target, FieldUtils.getFieldValue(source, field.getName()));
            } catch (IllegalAccessException e)
            {
                LOGGER.severe("deu merda na reflection hein gurizada, os campos tem que ter o mesmo nome no SOURCE e no TARGET");
            }
        };
    }

    private Predicate<Field> getEquivalentFields(TARGET target)
    {
        return field -> Arrays
                .stream(getTargetDeclaredFields(target))
                .anyMatch(haveFieldsSameName(field));
    }

    private Field[] getTargetDeclaredFields(TARGET target)
    {
        return target.getClass().getDeclaredFields();
    }

    private Field[] getSourceDeclaredFields(SOURCE source)
    {
        return source.getClass().getDeclaredFields();
    }

    private Predicate<Field> haveFieldsSameName(Field field)
    {
        return targetField -> targetField.getName().equals(field.getName());
    }
}