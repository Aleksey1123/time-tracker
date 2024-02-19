package com.timetracker.mapper;

import com.timetracker.entity.Task;
import com.timetracker.model.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {HistoryMapper.class})
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
    @Mapping(source = "assignedEmployees", target = "assignedEmployees", ignore = true)
    @Mapping(source = "history", target = "history", ignore = true)
    TaskDTO taskToTaskDTO(Task task);

    @Mapping(source = "assignedEmployees", target = "assignedEmployees", ignore = true)
    @Mapping(source = "history", target = "history", ignore = true)
    Task taskDTOToTask(TaskDTO taskDTO);
}
