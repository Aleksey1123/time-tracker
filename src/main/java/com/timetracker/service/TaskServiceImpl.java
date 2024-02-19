package com.timetracker.service;

import com.timetracker.enums.EntityNames;
import com.timetracker.mapper.TaskMapper;
import com.timetracker.model.TaskDTO;
import com.timetracker.repository.TaskRepository;
import com.timetracker.sorting.PageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final PageBuilder pageBuilder;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;

    @Override
    public Page<TaskDTO> getAllTasks(Integer pageNumber, Integer pageSize,
                                     String sortingParam) {
        PageRequest pageRequest = pageBuilder.buildPageRequest(pageNumber, pageSize,
                sortingParam, EntityNames.Task.name());

        return new PageImpl<>(taskRepository.findAll(pageRequest)
                .stream()
                .map(taskMapper::taskToTaskDTO)
                .collect(Collectors.toList()));
    }
}
