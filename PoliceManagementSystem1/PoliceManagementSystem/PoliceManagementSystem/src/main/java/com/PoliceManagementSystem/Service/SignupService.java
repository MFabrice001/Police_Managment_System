// SignupService.java
package com.PoliceManagementSystem.Service;

import com.PoliceManagementSystem.Model.Signup;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SignupService {
//    Signup createSignup(Signup signup, String url);

    Signup createSignup(Signup signup);

    List<Signup> getAllSignup();
    Signup login(Signup signup);
    void deleteSignup(UUID id);

    Optional<Signup> findById(UUID id);
    Page<Signup> getAllPolicePageable(int page, int size);

    Signup findSignupByPoliceNumber(Integer PoliceNumber);


}
