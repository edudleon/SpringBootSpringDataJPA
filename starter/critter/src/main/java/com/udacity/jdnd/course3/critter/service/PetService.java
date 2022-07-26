package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.exception.PetNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet save(Pet pet){
        Pet savedPed = petRepository.save(pet);
        Customer customer = savedPed.getCustomer();
        if(customer != null){
            customer.addPet(savedPed);
            customerRepository.save(customer);
        }
        return savedPed;
    }

    public Pet getPetById(Long petId){
        return petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> listPets(){
        return petRepository.findAll();
    }

    public List<Pet> listPetsByOwnerId(Long ownerId){
        // Search first for the Customer and then retrieve the Pet List
        Customer customer =  customerRepository.findById(ownerId).orElseThrow(PetNotFoundException::new);
        return customer.getPetList();
    }

}
