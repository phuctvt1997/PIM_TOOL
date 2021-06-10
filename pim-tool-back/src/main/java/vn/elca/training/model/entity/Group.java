package vn.elca.training.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GroupTable")

public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupLeader_id",nullable = false)
    private Employee groupLeader_id;

    @OneToMany(mappedBy = "groupID", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    @Version
    private Long version;


    public Group(Long id, Employee groupLeader_id) {
        this.id = id;
        this.groupLeader_id = groupLeader_id;
    }

    public Group() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getGroupLeader_id() {
        return groupLeader_id;
    }

    public void setGroupLeader_id(Employee groupLeader_id) {
        this.groupLeader_id = groupLeader_id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


}
