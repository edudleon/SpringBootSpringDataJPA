package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.controller.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToEntity(scheduleDTO);
        return convertEntityToScheduleDTO(scheduleService.save(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.listSchedules();
        return convertEntityToScheduleDTOList(scheduleList);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleList = scheduleService.getSchedulesByPetId(petId);
        return convertEntityToScheduleDTOList(scheduleList);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.getSchedulesByEmployeeId(employeeId);
        return convertEntityToScheduleDTOList(scheduleList);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.getSchedulesByCustomerId(customerId);
        return convertEntityToScheduleDTOList(scheduleList);
    }

    private static ScheduleDTO convertEntityToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        if(schedule.getEmployeeList() != null){
            List<Long> employeeIdList = new ArrayList<>();
            schedule.getEmployeeList().forEach((employee -> {
                employeeIdList.add(employee.getId());
            }));
            scheduleDTO.setEmployeeList(employeeIdList);
        }
        if(schedule.getPetList() != null){
            List<Long> petIdList = new ArrayList<>();
            schedule.getPetList().forEach((pet -> {
                petIdList.add(pet.getId());
            }));
            scheduleDTO.setPetList(petIdList);
        }
        return scheduleDTO;
    }

    private static List<ScheduleDTO> convertEntityToScheduleDTOList(List<Schedule> scheduleList){
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleList.forEach((s) -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(s, scheduleDTO);
            if(s.getEmployeeList() != null){
                List<Long> employeeList = new ArrayList<>();
                s.getEmployeeList().forEach((se)->{
                    employeeList.add(se.getId());
                });
                scheduleDTO.setEmployeeList(employeeList);
            }
            if(s.getPetList() != null){
                List<Long> petList = new ArrayList<>();
                s.getPetList().forEach((sp)->{
                    petList.add(sp.getId());
                });
                scheduleDTO.setPetList(petList);
            }
            scheduleDTOList.add(scheduleDTO);
        });
        return scheduleDTOList;
    }

    private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        if(scheduleDTO.getEmployeeList() != null){
            List<Employee> employeeList = new ArrayList<>();
            scheduleDTO.getEmployeeList().forEach((e) ->{
                employeeList.add(employeeService.getEmployeeById(e));
            });
            schedule.setEmployeeList(employeeList);
        }
        if(scheduleDTO.getPetList() != null){
            List<Pet> petList = new ArrayList<>();
            scheduleDTO.getPetList().forEach((p) ->{
                petList.add(petService.getPetById(p));
            });
            schedule.setPetList(petList);
        }
        return schedule;
    }
}
