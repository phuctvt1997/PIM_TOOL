package vn.elca.training.service;

import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.ConcurrentUpdateException;
import vn.elca.training.model.exception.ProjectNumberAlreadyExistException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author vlp
 */
public interface ProjectService {

    long count();

    List<Project> findProjectByProjectNumber(Integer projectNumber);

    Project findById_(Long id);

    List<Project> deleteAProject(Integer projectNumber);

    List<Project> searchProjectAccordingToCondition(String s, String status);

    List<Project> deleteMultipleProject(List<Integer> projectNumber);

    List<Project> findAllProject();

    ProjectDto addNewProjectDto(GroupDto group, Integer projectNumber, String name, LocalDate startingDate, LocalDate finishingDate, String customer, Project.Status projectStatus, Set<EmployeeDto> visa) throws ProjectNumberAlreadyExistException;

    ProjectDto updateProjectDto(GroupDto group, Integer projectNumber, String name, LocalDate startingDate, LocalDate finishingDate, String customer, Project.Status projectStatus, Set<EmployeeDto> visa, Long version) throws ConcurrentUpdateException;


}
