package vn.elca.training.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import vn.elca.training.dao.IGroupRepository;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@ContextConfiguration(classes = {ApplicationWebConfig.class})
//@RunWith(value=SpringRunner.class)
@DataJpaTest
@RunWith(value = SpringRunner.class)
public class ProjectRepositoryTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IGroupRepository iGroupRepository;

    @Test
    public void testCountAll() {
        projectRepository.save(new Project("LAGAPEO", LocalDate.now()));
        projectRepository.save(new Project("ZHQUEST", LocalDate.now()));
        projectRepository.save(new Project("SECUTIX", LocalDate.now()));
        Assert.assertEquals(3, projectRepository.count());
    }

    @Test
    public void testFindOneWithQueryDSL() {
        final String PROJECT_NAME = "Secutix";
        projectRepository.save(new Project(PROJECT_NAME, LocalDate.now()));
        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.name.eq(PROJECT_NAME))
                .fetchFirst();
        Assert.assertEquals(PROJECT_NAME, project.getName());
    }

    @Test
    public void testSaveOneProject() {
        final String projectName = "Secutix";
        final LocalDate date = LocalDate.now();
        iGroupRepository.save(new Project(projectName, date));
        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.name.eq(projectName))
                .fetchFirst();
        Assert.assertEquals(projectName, project.getName());
    }

    @Test
    public void testSaveMultipleProjects() {
        List<Project> projects = addFakeDataProject();
        iGroupRepository.saveAll(projects);
        List<Project> projectList = iGroupRepository.findAll();
        for (Project project : projectList) {
            System.out.println(project.getName() + " " + project.getFinishingDate());
        }
        Assert.assertEquals(projectList.size(), projects.size());
    }

    @Test
    public void testDeleteAProject() {
        List<Project> projects = addFakeDataProject();
        iGroupRepository.saveAll(projects);
        for (Project project : projects) {
            if (project.getName().equals("Secutix")) {
                iGroupRepository.delete(project);
            }
        }
        Assert.assertFalse(iGroupRepository.findAll().size() > projects.size());
    }

    public List<Project> addFakeDataProject() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "Secutix", LocalDate.now(), "ELCA", Project.Status.NEW));
        projects.add(new Project(2L, "DataAnlyst", LocalDate.now(), "EIU",Project.Status.NEW));
        projects.add(new Project(3L, "Bomb", LocalDate.now(), "DXC",Project.Status.FIN));
        projects.add(new Project(4L, "B52", LocalDate.now(), "FPT",Project.Status.PLA));
        projects.add(new Project(5L, "VNWAR", LocalDate.now(), "USA",Project.Status.INP));
        return projects;
    }

    public List<Group> addFakeDataGroup() {
        List<Group> groups = new ArrayList<>();
        List<Employee>employees = addFakeDataEmployee();
        groups.add(new Group(1L, employees.get(0)));
        groups.add(new Group(2L, employees.get(1)));
        groups.add(new Group(3L, employees.get(2)));
        groups.add(new Group(4L, employees.get(3)));
        return groups;
    }

    public List<Employee> addFakeDataEmployee() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "PTV", "Phuc", "Tran",LocalDate.now()));
        employees.add(new Employee(2L, "TPA", "Toyz", "Nguyen",LocalDate.now()));
        employees.add(new Employee(3L, "SKT", "Faker", "Lee",LocalDate.now()));
        employees.add(new Employee(4L, "RNG", "Uzi", "Tran",LocalDate.now()));
        return employees;
    }

    @Test
    public void getProjectWithQueryDSL() {
        final String projectName = "Secutix";
        final String customer = "ELCA";
        List<Project> projects = addFakeDataProject();
        iGroupRepository.saveAll(projects);
        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.customer.eq(customer), QProject.project.name.eq(projectName))
                .fetchFirst();
        Assert.assertEquals("Secutix", project.getName());
    }

//    @Test
//    public void getProjectWithComplexDSL() {
//        List<Project> projects = addFakeDataProject();
//        iGroupRepository.saveAll(projects);
//        Project project = new JPAQuery<Project>(em)
//                .from(QProject.project)
//                .where(QProject.project.customer.eq("ELCA"), QProject.project.name.eq("Secutix"))
//                .fetchFirst();
//        Assert.assertEquals("ELCA",project.getCustomer());
//    }
}