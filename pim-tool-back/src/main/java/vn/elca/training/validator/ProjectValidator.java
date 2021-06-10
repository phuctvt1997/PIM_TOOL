package vn.elca.training.validator;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;
import vn.elca.training.model.exception.ProjectNumberAlreadyExistException;
import vn.elca.training.repository.ProjectRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Component
public class ProjectValidator {
    @Autowired
    ProjectRepository repository;

    @PersistenceContext
    EntityManager em;

    public void validate(Project entity) throws ProjectNumberAlreadyExistException {
        List<Project> projects = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(entity.getProjectNumber())).fetch();
        if(projects.size()!=0){
            throw new ProjectNumberAlreadyExistException(entity.getProjectNumber());
        }
    }
}
