package com.timetracker.sorting;

import com.timetracker.enums.EntityNames;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class PageBuilder {

    // page number
    private static final int DEFAULT_PAGE = 0;

    // is a maximum size of the page
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final Set<String> EMPLOYEE_FIELDS = Set.of("id", "firstName", "lastName");
    private final Set<String> TASK_FIELDS = Set.of("id", "name");
    private final Set<String> HISTORY_FIELDS = Set.of("id", "status");
    private final Set<String> ENTITIES = Set.of(EntityNames.Employee.name(),
            EntityNames.Task.name(),
            EntityNames.History.name());

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize,
                                        String sortingParam, String entityName) {

        int queryPageNumber = DEFAULT_PAGE;
        int queryPageSize = DEFAULT_PAGE_SIZE;
        StringBuilder querySortingParam = new StringBuilder();

        querySortingParam.append(Objects.requireNonNullElse(sortingParam, "id"));

        if (ENTITIES.contains(entityName)) {
            if ((entityName.equals(EntityNames.Employee.name()) && !EMPLOYEE_FIELDS.contains(querySortingParam.toString())) ||
                    (entityName.equals(EntityNames.Task.name()) && !TASK_FIELDS.contains(querySortingParam.toString())) ||
                    (entityName.equals(EntityNames.History.name()) && !HISTORY_FIELDS.contains(querySortingParam.toString())))
                throw new IllegalStateException("Unexpected value: " + sortingParam +
                        "for class " + entityName);
        }

        // in case pageNumber is null adding null check
        if (pageNumber != null && pageNumber > DEFAULT_PAGE)
            queryPageNumber = pageNumber - 1;

        // in case pageSize is null adding null check
        if (pageSize != null && pageSize > 0 && pageSize < DEFAULT_PAGE_SIZE)
            queryPageSize = pageSize;

        return PageRequest.of(queryPageNumber,
                queryPageSize,
                Sort.by(Sort.Direction.ASC, querySortingParam.toString()));
    }
}