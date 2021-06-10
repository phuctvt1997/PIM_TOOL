package vn.elca.training.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 3,nullable = false)
    private String visa;
    @Column
    private String firstName;
    @Column
    private String lastName;
    private LocalDate birth_Date;

    @Version
    private Long version;

//    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
//    private Set<Project_Employee> projects = new HashSet<>();

    @ManyToMany(mappedBy = "employees")
    private Set<Project> projects;

    public Employee(Long id, String visa, String firstName, String lastName, LocalDate birth_Date) {
        this.id = id;
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth_Date = birth_Date;
    }

    public Employee(Long id, String visa, String firstName, String lastName, LocalDate birth_Date, Set<Project> projects) {
        this.id = id;
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth_Date = birth_Date;
        this.projects = projects;
    }

    public Employee() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirth_Date() {
        return birth_Date;
    }

    public void setBirth_Date(LocalDate birth_Date) {
        this.birth_Date = birth_Date;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
