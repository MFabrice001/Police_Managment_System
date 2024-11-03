package com.PoliceManagementSystem.Service.Impl;

import com.PoliceManagementSystem.Model.Signup;
import com.PoliceManagementSystem.Service.SignupService;
import com.PoliceManagementSystem.repo.SignupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class SignupServiceImpl implements SignupService {
    @Autowired
    SignupRepo signupRepo;

    @Override
    public Signup createSignup(Signup signup) {

        return signupRepo.save(signup);
    }

    @Override
    public List<Signup> getAllSignup() {
        return signupRepo.findAll(); // Get all users from database
    }

    @Override
    public Signup login(Signup signup) {
        // Implement login logic
        return signupRepo.findByUsernameAndPassword(signup.getUsername(), signup.getPassword());
    }
    @Override
    public void deleteSignup(UUID id) {
        signupRepo.deleteById(id); // Delete user by ID
    }


    @Override
    public Optional<Signup> findById(UUID id) {
        return signupRepo.findById(id);
    }

    @Override
    public Page<Signup> getAllPolicePageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return signupRepo.findAll(pageable);
    }

    @Override
    public Signup findSignupByPoliceNumber(Integer PoliceNumber) {
        return signupRepo.findSignupByPoliceNumber(PoliceNumber);

    }

}