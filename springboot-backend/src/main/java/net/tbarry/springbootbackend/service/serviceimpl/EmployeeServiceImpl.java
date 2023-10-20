package net.tbarry.springbootbackend.service.serviceimpl;

import java.util.List;
// import java.util.Optional;

import org.springframework.stereotype.Service;

import net.tbarry.springbootbackend.Employee;
import net.tbarry.springbootbackend.repository.EmployeeRepository;
import net.tbarry.springbootbackend.resource.ResourceNotFoundException;
import net.tbarry.springbootbackend.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {

        // Optional<Employee> employee = employeeRepository.findById(id);
        // if (employee.isPresent()) {
        // return employee.get();
        // } else {
        // throw new ResourceNotFoundException("Employee", "Id", id);
        // }

        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
    }

    @Override
    public Employee updateEmployee(Employee employee, long id) {
        // we need to check wheter employee with given id is exist in DB or not
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        // save existing employee to DB
        employeeRepository.save(existingEmployee);

        return existingEmployee;
    }

    @Override
    public void deleteEmployee(long id) {
        // check wheter a employee exist in a DB or not
        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
        employeeRepository.deleteById(id);
    }

}
