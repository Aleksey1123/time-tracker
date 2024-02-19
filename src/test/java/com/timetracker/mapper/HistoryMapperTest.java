package com.timetracker.mapper;

import com.timetracker.entity.History;
import com.timetracker.model.HistoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HistoryMapperTest {

    private History history;
    private HistoryDTO historyDTO;

    @BeforeEach
    void setUp() {

        history = History.builder()
                .id(1)
                .build();

        historyDTO = HistoryDTO.builder()
                .id(1)
                .build();
    }

    @Test
    void historyToHistoryDTO() {

        HistoryDTO historyDTO = HistoryMapper.INSTANCE.historyToHistoryDTO(history);
        assertThat(history).usingRecursiveComparison()
                .isEqualTo(historyDTO);
    }

    @Test
    void historyDTOToHistory() {

        History history = HistoryMapper.INSTANCE.historyDTOToHistory(historyDTO);
        assertThat(historyDTO).usingRecursiveComparison()
                .isEqualTo(history);
    }
}