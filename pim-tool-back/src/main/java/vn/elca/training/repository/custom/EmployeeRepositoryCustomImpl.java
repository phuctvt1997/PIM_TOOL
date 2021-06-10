package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.QEmployee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Employee> findEmployeeByVisaDto(Set<EmployeeDto> visaList) {
        List<Employee> employees = new JPAQuery<Employee>(em).from(QEmployee.employee).where(QEmployee.employee.visa.in(visaList.stream().map(s -> s.getVisa()).collect(Collectors.toList()))).fetch();
        return new HashSet<>(employees);
    }
}
