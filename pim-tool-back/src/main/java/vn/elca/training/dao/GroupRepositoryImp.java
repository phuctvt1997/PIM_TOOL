package vn.elca.training.dao;

import org.springframework.stereotype.Service;

@Service
public class GroupRepositoryImp{
//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public List<Project> saveOneProject(long id,String name, LocalDate date) {
//        List<Project> projects = findProjectByID(id);
//        for (Project project : projects) {
//            project.setName(name);
//            project.setFinishingDate(date);
//            em.getTransaction().begin();
//            em.persist(project);
//            em.getTransaction().commit();
//        }
//        return em;
//    }
//
//    @Override
//    public List<Project> findProjectByID(long id) {
//        return new JPAQuery<Project>(em)
//                .from(QProject.project)
//                .innerJoin(QProject.project.tasks, QTask.task)
//                .where(QTask.task.id.eq(id))
//                .fetch();
//    }
//
//    @Override
//    public List<Project> findProjectByName(String name) {
//        return new JPAQuery<Project>(em)
//                .from(QProject.project)
//                .innerJoin(QProject.project.tasks, QTask.task)
//                .where(QTask.task.name.eq(name))
//                .fetch();
//    }
}
