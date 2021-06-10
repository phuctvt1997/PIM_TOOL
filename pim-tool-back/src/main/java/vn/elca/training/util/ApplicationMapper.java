package vn.elca.training.util;

import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.*;
import vn.elca.training.model.entity.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gtn
 */
@Component
public class ApplicationMapper {

    public ApplicationMapper() {
        // Mapper utility class
    }

    public EmployeeDto employeeToEmployeeDto(Employee entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setVisa(entity.getVisa());
        dto.setBirth_Date(entity.getBirth_Date());
        return dto;
    }



    public Set<EmployeeDto> employeeToSetEmployeeDto(Set<Employee> employeeSet){
        Set<EmployeeDto> dtoSet= new HashSet<>();
        for (Employee employee : employeeSet) {
            dtoSet.add(employeeToEmployeeDto(employee));
        }
        return dtoSet;
    }


    public GroupDto groupToGroupDto(Group entity) {
        GroupDto dto = new GroupDto();
        dto.setId(entity.getId());
        dto.setGroupLeaderId(entity.getGroupLeader_id());
        return dto;
    }

    public ProjectDto projectToFullProjectDto(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCustomer(entity.getCustomer());
        dto.setStartingDate(entity.getStartingDate());
        dto.setFinishingDate(entity.getFinishingDate());
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setGroup(groupToGroupDto(entity.getGroupID()));
        dto.setEmployee(employeeToSetEmployeeDto(entity.getEmployees()));
        dto.setVersion(entity.getVersion());

        return dto;
    }

    public ProjectDto projectToProjectList(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCustomer(entity.getCustomer());
        dto.setStartingDate(entity.getStartingDate());
        dto.setProjectNumber(entity.getProjectNumber());
        return dto;
    }

    public ProjectDto projectToProjectDto(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFinishingDate(entity.getFinishingDate());
        return dto;
    }

    public ProjectDto projectToProjectDtoContainCustomerName(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFinishingDate(entity.getFinishingDate());
        dto.setCustomer(entity.getCustomer());
        return dto;
    }

    public TaskDto taskToTaskDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTaskName(task.getName());
        dto.setDeadline(task.getDeadline());
        dto.setProjectName(task.getProject() != null
                ? task.getProject().getName()
                : null);

        return dto;
    }

    public UserDto userToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setTasks(user.getTasks().stream().map(this::taskToTaskDto).collect(Collectors.toList()));
        return dto;
    }
}
