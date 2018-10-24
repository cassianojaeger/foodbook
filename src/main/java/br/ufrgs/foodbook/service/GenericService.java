package br.ufrgs.foodbook.service;

import org.springframework.data.domain.Page;

public interface GenericService<T, V>
{
    Page<V> getPaginatedData(int page, int size);

    void create(T obj);

    void update(T obj);

    void remove(T obj);
}
