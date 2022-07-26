package com.udacity.jdnd.course3.critter.controller.dto;

import com.udacity.jdnd.course3.critter.util.EmployeeSkill;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
    private List<Long> employeeList;
    private List<Long> petList;
    private LocalDate date;
    private Set<EmployeeSkill> activities;

    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public List<Long> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Long> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Long> getPetList() {
        return petList;
    }

    public void setPetList(List<Long> petList) {
        this.petList = petList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
