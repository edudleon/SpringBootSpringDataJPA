package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.util.EmployeeSkill;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long employeeId){
        return employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }

    public void updateEmployeeAvailability(Set<DayOfWeek> daysAvailable, @PathVariable long employeeId){
        employeeRepository.findById(employeeId).map(employeeToBeUpdated -> {
            employeeToBeUpdated.setAvailability(daysAvailable);
            return employeeRepository.save(employeeToBeUpdated);
        }).orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findEmployeesByAvailabilityAndSkills(Integer dayOfTheWeek, Set<EmployeeSkill> skills){
        List<Employee> availableEmployee = new ArrayList<>();
        List<Employee> filteredEmployee = new ArrayList<>();
        employeeRepository.findBySkillsIn(skills).forEach((e) ->{
            if(e.getSkills().containsAll(skills)){
                filteredEmployee.add(e);
            }
        });
        filteredEmployee.forEach((e) -> {
            if(e.getAvailability().contains(DayOfWeek.of(dayOfTheWeek))){
                availableEmployee.add(e);
            }
        });
        return availableEmployee;
    }
}
