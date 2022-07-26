package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public List<Customer> list(){
        return  customerRepository.findAll();
    }

    public Customer findById(Long customerId){
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }


    public Customer findByPetId(Long petId){
        // Search first for the pet and then retrieve the customer
        Pet pet = petRepository.findById(petId).orElseThrow(CustomerNotFoundException::new);
        return pet.getCustomer();
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
}
