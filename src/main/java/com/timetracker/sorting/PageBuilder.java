package com.timetracker.sorting;

import com.timetracker.enums.EntityNames;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PageBuilder {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final Set<String> FIELDS = Set.of("id", "firstName", "lastName",
            "name", "status");
    private final Set<String> ENTITIES = Set.of(EntityNames.Employee.name(),
            EntityNames.Task.name(),
            EntityNames.History.name());

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize,
                                        String sortingParam, String entityName) {
        int queryPageNumber = pageNumber;
        int queryPageSize = pageSize;

        if (ENTITIES.contains(entityName))
            if (!FIELDS.contains(sortingParam))
                throw new IllegalStateException("Unexpected value: " + sortingParam +
                        "for class " + entityName);

        if (pageNumber > 0) {
            queryPageNumber -= 1;
        }
        else queryPageNumber = DEFAULT_PAGE;

        if (pageSize > DEFAULT_PAGE_SIZE) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }

        return PageRequest.of(queryPageNumber,
                queryPageSize,
                Sort.by(Sort.Direction.ASC, sortingParam));
    }
}