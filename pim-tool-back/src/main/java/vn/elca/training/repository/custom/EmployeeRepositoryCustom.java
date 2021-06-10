package vn.elca.training.repository.custom;

import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.model.entity.Employee;

import java.util.Set;

public interface EmployeeRepositoryCustom {
    Set<Employee> findEmployeeByVisaDto (Set<EmployeeDto>visaList);
}
