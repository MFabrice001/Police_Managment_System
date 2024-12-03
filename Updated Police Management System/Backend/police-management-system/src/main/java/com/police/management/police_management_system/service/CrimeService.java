package com.police.management.police_management_system.service;

import com.police.management.police_management_system.model.Crime;
import com.police.management.police_management_system.repository.CrimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrimeService {

    private final CrimeRepository crimeRepository;

    public CrimeService(CrimeRepository crimeRepository) {
        this.crimeRepository = crimeRepository;
    }

    public Crime reportCrime(Crime crime) {
        return crimeRepository.save(crime);
    }

    public List<Crime> getAllCrimes() {
        return crimeRepository.findAll();
    }

    public Crime getCrimeById(Long id) {
        return crimeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Crime not found with ID: " + id));
    }

    public void deleteCrime(Long id) {
        crimeRepository.deleteById(id);
    }
}
