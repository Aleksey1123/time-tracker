package com.timetracker.sorting;

import com.timetracker.enums.EntityNames;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PageBuilderTest {

    @Autowired
    private PageBuilder pageBuilder;

    @Test
    void buildPageRequestWithNullParamsShouldWork() {

        PageRequest pageRequest = pageBuilder.buildPageRequest(null, null,
                null, EntityNames.Task.toString());
        assertEquals(pageRequest, PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")));
    }

    @Test
    void buildPageRequest() {
        PageRequest pageRequest = pageBuilder.buildPageRequest(1, 0,
                "id", EntityNames.Task.toString());
        assertEquals(pageRequest, PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")));
    }
}