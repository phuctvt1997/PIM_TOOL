package vn.elca.training.model.dto;

import vn.elca.training.model.entity.Employee;

public class GroupDto {
    private Long id;
    private Employee groupLeaderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getGroupLeaderId() {
        return groupLeaderId;
    }

    public void setGroupLeaderId(Employee groupLeaderId) {
        this.groupLeaderId = groupLeaderId;
    }
}
