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

    private final Set<String> EMPLOYEE_FIELDS = Set.of("id", "firstName", "lastName");
    private final Set<String> TASK_FIELDS = Set.of("id", "name");
    private final Set<String> HISTORY_FIELDS = Set.of("id", "status");
    private final Set<String> ENTITIES = Set.of(EntityNames.Employee.name(),
            EntityNames.Task.name(),
            EntityNames.History.name());

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize,
                                        String sortingParam, String entityName) {
        int queryPageNumber = pageNumber;
        int queryPageSize = pageSize;

        if (ENTITIES.contains(entityName)) {
            if ((entityName.equals(EntityNames.Employee.name()) && !EMPLOYEE_FIELDS.contains(sortingParam)) ||
                    (entityName.equals(EntityNames.Task.name()) && !TASK_FIELDS.contains(sortingParam)) ||
                    (entityName.equals(EntityNames.History.name()) && !HISTORY_FIELDS.contains(sortingParam)))
                throw new IllegalStateException("Unexpected value: " + sortingParam +
                        "for class " + entityName);
        }

        if (pageNumber > 0) {
            queryPageNumber -= 1;
        }
        else queryPageNumber = DEFAULT_PAGE;

        if (pageSize > DEFAULT_PAGE_SIZE || pageSize == 0) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }

        return PageRequest.of(queryPageNumber,
                queryPageSize,
                Sort.by(Sort.Direction.ASC, sortingParam));
    }
}