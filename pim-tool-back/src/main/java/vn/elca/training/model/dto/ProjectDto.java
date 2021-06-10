package vn.elca.training.model.dto;

import vn.elca.training.model.entity.Project;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author gtn
 *
 */
public class ProjectDto {
    private Long id;

    @NotNull
    private String name;
    private LocalDate finishingDate;
    @NotNull
    private String customer;
    @NotNull
    private Integer projectNumber;
    @NotNull
    private Project.Status status;
    @NotNull
    private LocalDate startingDate;
    @NotNull
    private GroupDto group;
    private Set<EmployeeDto> employee;
    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Set<EmployeeDto> getEmployee() {
        return employee;
    }

    public void setEmployee(Set<EmployeeDto> employee) {
        this.employee = employee;
    }

    public GroupDto getGroup() {
        return group;
    }

    public void setGroup(GroupDto group) {
        this.group = group;
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Project.Status getStatus() {
        return status;
    }

    public void setStatus(Project.Status status) {
        this.status = status;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(LocalDate finishingDate) {
        this.finishingDate = finishingDate;
    }


    public ProjectDto() {
    }
}
