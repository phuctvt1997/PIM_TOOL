package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Employees")
public class EmployeeController extends AbstractApplicationController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/")
    public List<EmployeeDto> EmployeeList() {
        return this.employeeService.findAllEmployee().stream().map(mapper::employeeToEmployeeDto).collect(Collectors.toList());
    }
}
