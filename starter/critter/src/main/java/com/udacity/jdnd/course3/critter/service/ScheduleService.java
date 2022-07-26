package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.exception.ScheduleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule save(Schedule schedule){
        Schedule newSchedule = scheduleRepository.save(schedule);
        if(newSchedule.getEmployeeList() != null){
            newSchedule.getEmployeeList().forEach((e) ->{
                e.addSchedule(newSchedule);
                employeeRepository.save(e);
            });
        }
        if(newSchedule.getPetList() != null){
            newSchedule.getPetList().forEach((p) ->{
                p.addSchedule(newSchedule);
                petRepository.save(p);
            });
        }
        return newSchedule;
    }

    public List<Schedule> listSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesByPetId(Long petId){
        Pet pet =petRepository.findById(petId).orElseThrow(ScheduleNotFoundException::new);
        return pet.getSchedules();
    }

    public List<Schedule> getSchedulesByEmployeeId(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(ScheduleNotFoundException::new);
        return employee.getSchedules();
    }

    public List<Schedule> getSchedulesByCustomerId(Long customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(ScheduleNotFoundException::new);
        List<Schedule> scheduleList = new ArrayList<Schedule>();
        customer.getPetList().forEach((Pet pet)->
            scheduleList.addAll(pet.getSchedules()));
        return scheduleList;
    }
}
