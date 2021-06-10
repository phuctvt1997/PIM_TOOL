package vn.elca.training.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author vlp
 */
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupid")
    private Group groupID;
    @Column(length = 4,unique = true)
    @Min(1)
    @Max(9999)
    private Integer projectNumber;
    @Column(nullable = false)
//    @NotNull
    private String name;
    @Column
//    @NotNull
    private String customer;
    @Column(length = 3)
//    @NotNull
    private Status status;
    @Column
//    @NotNull
    private LocalDate startingDate;
    @Column
    private LocalDate finishingDate;
    @Version
    private Long version;


    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany
//    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "Project_Employee",
            joinColumns = {@JoinColumn(name = "ProjectID")},
            inverseJoinColumns = {@JoinColumn(name = "EmployeeID")})
    private Set<Employee> employees;

    public enum Status{
        NEW,PLA,INP,FIN;
    }

    public Project() {
    }

    public Project(Group groupID, Integer projectNumber, String name, String customer, Status status, LocalDate startingDate, LocalDate finishingDate) {
        this.groupID = groupID;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startingDate = startingDate;
        this.finishingDate = finishingDate;

    }

    public Project(Long id, Group group, Integer projectNumber, String name, String customer, LocalDate startingDate, Long version) {
        this.groupID = group;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.startingDate = startingDate;
        this.version = version;
    }

    public Project(String name, LocalDate finishingDate) {
        this.name = name;
        this.finishingDate = finishingDate;
    }

    public Project(Long id, String name, LocalDate finishingDate) {
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
    }

        public Project(Long id, String name, LocalDate finishingDate, String customer,Status status) {
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
        this.customer = customer;
        this.status =status;
    }


    public Project(Long id, String name, String customer, Status status, LocalDate startingDate, LocalDate finishingDate) {
        this.id = id;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startingDate = startingDate;
        this.finishingDate = finishingDate;
    }

    public Project(Integer projectNumber, String name, String customer, Status status, LocalDate startingDate, LocalDate finishingDate) {
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startingDate = startingDate;
        this.finishingDate = finishingDate;
    }

    public Project(Group groupID, Integer projectNumber, String name, String customer, Status status, LocalDate startingDate, LocalDate finishingDate, Set<Employee> employees) {
        this.groupID = groupID;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startingDate = startingDate;
        this.finishingDate = finishingDate;
        this.employees = employees;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Group getGroupID() {
        return groupID;
    }

    public void setGroupID(Group group) {
        this.groupID = group;
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}