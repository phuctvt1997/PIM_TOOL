package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.ConcurrentUpdateException;
import vn.elca.training.model.exception.ProjectNumberAlreadyExistException;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.GroupService;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;
import vn.elca.training.validator.ConcurrentValidator;
import vn.elca.training.validator.ProjectValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author vlp
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectValidator projectValidator;
    @Autowired
    private ConcurrentValidator concurrentValidator;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ApplicationMapper mapper;

    @Override
    public long count() {
        return this.repository.findAll().size();
    }


    @Override
    public List<Project> findProjectByProjectNumber(Integer projectNumber) {
        return repository.findProjectByProjectNumber(projectNumber);
    }

    @Override
    public Project findById_(Long id) {
        return repository.findProjectById(id);
    }

    @Override
    public List<Project> deleteAProject(Integer projectNumber) {
        List<Project> projects = findProjectByProjectNumber(projectNumber);
        repository.deleteAll(projects);
        return projects;
    }

    @Override
    public List<Project> searchProjectAccordingToCondition(String s, String status) {
        return repository.findProjectByCondition(s, status);
    }

    @Override
    public List<Project> deleteMultipleProject(List<Integer> projectNumber) {
        List<Project> projects = repository.findMultipleProject(projectNumber);
        repository.deleteAll(projects);
        return projects;
    }

    @Override
    public List<Project> findAllProject() {
        return repository.findAllProject();
    }


    @Override
    public ProjectDto addNewProjectDto(GroupDto group, Integer projectNumber, String name, LocalDate startingDate, LocalDate finishingDate, String customer, Project.Status projectStatus, Set<EmployeeDto> visa) throws ProjectNumberAlreadyExistException {
        Group group1 = groupService.getOneGroup(group.getId());
        Set<Employee> employeeSet = employeeRepository.findEmployeeByVisaDto(visa);
        Project project = new Project(group1, projectNumber, name, customer, projectStatus, startingDate, finishingDate, employeeSet);
        projectValidator.validate(project);
        Project savedProject = repository.save(project);
        return mapper.projectToFullProjectDto(savedProject);
    }

    @Override
    public ProjectDto updateProjectDto(GroupDto group, Integer projectNumber, String name, LocalDate startingDate, LocalDate finishingDate, String customer, Project.Status projectStatus, Set<EmployeeDto> visa, Long version) throws ConcurrentUpdateException {
        Project project = repository.findProjectByNumber(projectNumber);
        concurrentValidator.validate(project.getVersion(), version,projectNumber);
        project.setGroupID(groupService.getOneGroup(group.getId()));
        project.setProjectNumber(projectNumber);
        project.setStatus(projectStatus);
        project.setName(name);
        project.setCustomer(customer);
        project.setFinishingDate(finishingDate);
        project.setStartingDate(startingDate);
        project.setEmployees(employeeRepository.findEmployeeByVisaDto(visa));
        return mapper.projectToFullProjectDto(repository.save(project));
    }
}
