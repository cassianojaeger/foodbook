package br.ufrgs.foodbook.util;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Math.min;

@Service
public class PaginationUtils<T>
{

    public PageImpl<T> buildPage(int page, int size, Set<T> set)
    {
        return new PageImpl<>(
                paginate(page, size, set),
                PageRequest.of(page, size),
                set.size()
        );
    }

    private List<T> paginate(int page, int size, Set<T> list)
    {
        return new ArrayList(list).subList(page*size, min((page*size)+size, list.size()));
    }
}
