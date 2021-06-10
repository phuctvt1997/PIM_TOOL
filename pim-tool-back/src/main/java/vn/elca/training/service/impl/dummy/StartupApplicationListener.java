package vn.elca.training.service.impl.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.repository.ProjectRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class StartupApplicationListener {
    @Autowired
    private ProjectRepository repository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    Set<Employee> employeeSet = new HashSet<>();

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {

        employeeSet.add(employeeRepository.getOne(1L));
        employeeSet.add(employeeRepository.getOne(2L));
        employeeSet.add(employeeRepository.getOne(3L));
        groupRepository.save(new Group(1L, employeeRepository.save(new Employee(1L, "PTV", "Phuc", "Tran", LocalDate.now()))));
        groupRepository.save(new Group(2L, employeeRepository.save(new Employee(2L, "TNT", "Truc", "Ngo", LocalDate.now()))));
        groupRepository.save(new Group(3L, employeeRepository.save(new Employee(3L, "SND", "Sinh", "Nguyen", LocalDate.now()))));
        groupRepository.save(new Group(4L, employeeRepository.save(new Employee(4L, "CDN", "Chau", "Nguyen", LocalDate.now()))));
        groupRepository.save(new Group(5L, employeeRepository.save(new Employee(5L, "DNT", "Dang", "Nguyen", LocalDate.now()))));

        employeeRepository.save(new Employee(6L, "TVP", "Phat", "Tran", LocalDate.now()));
        employeeRepository.save(new Employee(7L, "VDT", "Dep", "Vo", LocalDate.now()));
        employeeRepository.save(new Employee(8L, "HIV", "Huong", "Viet", LocalDate.now()));

        repository.save(new Project(groupRepository.getOne(1L), 1234, "secutix", "Phuc", Project.Status.NEW, LocalDate.now(), LocalDate.now().plusDays(10), employeeSet));
        repository.save(new Project(groupRepository.getOne(2L), 2345, "AiMachine", "Truc", Project.Status.NEW, LocalDate.now(), LocalDate.now().plusDays(13), employeeSet));
        repository.save(new Project(groupRepository.getOne(3L), 3456, "TrafficLight", "Phu", Project.Status.PLA, LocalDate.now(), LocalDate.now().plusDays(15), employeeSet));
        repository.save(new Project(groupRepository.getOne(4L), 6789, "Fatman", "Sinh", Project.Status.NEW, LocalDate.now(), LocalDate.now().plusDays(19), employeeSet));
        repository.save(new Project(groupRepository.getOne(2L), 8910, "secutix2", "Son", Project.Status.INP, LocalDate.now(), LocalDate.now().plusDays(10), employeeSet));
        repository.save(new Project(groupRepository.getOne(2L), 9876, "PentaKill", "Phat", Project.Status.FIN, LocalDate.now(), LocalDate.now().plusDays(29), employeeSet));


    }
}
