package com.timetracker.mapper;

import com.timetracker.entity.Employee;
import com.timetracker.model.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TaskMapper.class, HistoryMapper.class})
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    @Mapping(source = "currentTask", target = "currentTask")
    @Mapping(source = "history", target = "history")
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    @Mapping(source = "currentTask", target = "currentTask")
    @Mapping(source = "history", target = "history")
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
