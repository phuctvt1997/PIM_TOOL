package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QGroup;
import vn.elca.training.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    public Project findProjectById(Long id) {
        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .leftJoin(QProject.project.groupID, QGroup.group)
                .fetchJoin()
                .where(QProject.project.id.eq(id))
                .fetchOne();
        System.out.println();
        return project;
    }

    @Override
    public List<Project> findProjectByCondition(String s, String sStatus) {
        List<Project> projects;
        if (s.equals("") && !sStatus.equals("")) {
            projects = findByStatus(sStatus);
        } else if (sStatus.equals("") && !s.equals("")) {
            projects = findByStringCondition(s);
        } else {
            projects = findByConditionAndStatus(s, sStatus);
        }
        return projects;
    }

    List<Project> findByStatus(String status) {
        return new JPAQuery<Project>(em).from(QProject.project)
                .where(QProject.project.status.eq(Project.Status.valueOf(status))).fetch();
    }

    List<Project> findByStringCondition(String condition) {
        return new JPAQuery<Project>(em).from(QProject.project)
                .where(QProject.project.projectNumber.like("%" + condition + "%")
                        .or(QProject.project.customer.like("%" + condition + "%"))
                        .or(QProject.project.name.like("%" + condition + "%")))
                .fetch();
    }

    List<Project> findByConditionAndStatus(String condition, String status) {
        return new JPAQuery<Project>(em).from(QProject.project)
                .where(QProject.project.status.eq(Project.Status.valueOf(status))
                        .and(QProject.project.projectNumber.like("%" + condition + "%")
                                .or(QProject.project.customer.like("%" + condition + "%"))
                                .or(QProject.project.name.like("%" + condition + "%"))))
                .fetch();
    }

    @Override
    public List<Project> findAllProject() {
        return new JPAQuery<Project>(em).from(QProject.project).orderBy(QProject.project.projectNumber.asc()).fetch();
    }

    @Override
    public List<Project> findProjectByProjectNumber(Integer projectNumber) {
        return new JPAQuery<Project>(em).from(QProject.project).where(QProject.project.projectNumber.eq(projectNumber)).fetch();
    }

    @Override
    public List<Project> findMultipleProject(List<Integer> projects) {
        return new JPAQuery<Project>(em).from(QProject.project).where(QProject.project.projectNumber.in(projects)).fetch();
    }

    @Override
    public Project findProjectByNumber(Integer projectNumber) {
        return new JPAQuery<Project>(em).from(QProject.project).where(QProject.project.projectNumber.eq(projectNumber)).fetchOne();
    }

}
