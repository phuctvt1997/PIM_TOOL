package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.ConcurrentUpdateException;
import vn.elca.training.model.exception.ProjectNumberAlreadyExistException;
import vn.elca.training.service.ProjectService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gtn
 */
@RestController
@RequestMapping("/projects")
public class ProjectController extends AbstractApplicationController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/search/{id}")
    public List<ProjectDto> searchID(@PathVariable Long id) {
        List<Project> projects = new ArrayList<>();
        projects.add(projectService.findById_(id));
        return projects.stream().map(mapper::projectToFullProjectDto).collect(Collectors.toList());
    }

    @PutMapping("/update")
    public ProjectDto update(@Valid @RequestBody ProjectDto project) throws ConcurrentUpdateException {
        return projectService.updateProjectDto(project.getGroup(), project.getProjectNumber(), project.getName()
                , project.getStartingDate(), project.getFinishingDate(),
                project.getCustomer(), project.getStatus(), project.getEmployee(), project.getVersion());
    }

    @InitBinder("Project")
    public void customizeBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setLenient(false);
        binder.registerCustomEditor(LocalDate.class, "finishingDate",
                new CustomDateEditor(dateFormatter, true));
        binder.registerCustomEditor(LocalDate.class, "startingDate",
                new CustomDateEditor(dateFormatter, true));
    }

    @PostMapping("/")
    public ProjectDto addNewProject(@Valid @RequestBody ProjectDto project) throws ProjectNumberAlreadyExistException{
        return projectService.addNewProjectDto(project.getGroup(), project.getProjectNumber(),
                project.getName(), project.getStartingDate(), project.getFinishingDate()
                , project.getCustomer(), project.getStatus(), project.getEmployee());
    }

    @GetMapping("/")
    public List<ProjectDto> findALlProject() {
        return projectService.findAllProject().stream().map(mapper::projectToFullProjectDto).collect(Collectors.toList());
    }

    @GetMapping("/{projectNumber}")
    public List<ProjectDto> findProjectByProjectNumber(@PathVariable Integer projectNumber) {
        return projectService.findProjectByProjectNumber(projectNumber).stream()
                .map(mapper::projectToFullProjectDto).collect(Collectors.toList());
    }

    @DeleteMapping("/delete/{projectNumber}")
    public List<ProjectDto> deleteAProject(@Valid @PathVariable Integer projectNumber) {
        return projectService.deleteAProject(projectNumber).stream()
                .map(mapper::projectToProjectList).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<ProjectDto> searchCondition(@RequestParam(name = "StringCondition") String stringCondition,
                                            @RequestParam(name = "StringStatus") String stringStatus) {
        return projectService.searchProjectAccordingToCondition(stringCondition, stringStatus).stream()
                .map(mapper::projectToFullProjectDto).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public List<ProjectDto> deleteMultipleProject(@RequestBody List<Integer> projectNumberNeedToDelete) {
        return this.projectService.deleteMultipleProject(projectNumberNeedToDelete)
                .stream().map(mapper::projectToProjectList).collect(Collectors.toList());
    }
}
