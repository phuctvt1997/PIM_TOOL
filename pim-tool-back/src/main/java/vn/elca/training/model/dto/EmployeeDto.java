package vn.elca.training.model.dto;

import java.time.LocalDate;

public class EmployeeDto {
    private Long id;
    private String visa;
    private String firstName;
    private String lastName;
    private LocalDate birth_Date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
