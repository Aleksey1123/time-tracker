package com.timetracker.mapper;

import com.timetracker.entity.History;
import com.timetracker.model.HistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HistoryMapper {

    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);
    @Mapping(source = "historyList", target = "historyList", ignore = true)
    @Mapping(source = "employee", target = "employee", ignore = true)
    HistoryDTO historyToHistoryDTO(History history);
    @Mapping(source = "historyList", target = "historyList", ignore = true)
    @Mapping(source = "employee", target = "employee", ignore = true)
    History historyDTOToHistory(HistoryDTO historyDTO);
}
