package com.PoliceManagementSystem.Service.Impl;

import com.PoliceManagementSystem.Model.Request;
import com.PoliceManagementSystem.Service.RequestService;
import com.PoliceManagementSystem.repo.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RequestServiceImpl implements RequestService {
   @Autowired
   RequestRepo requestRepo;

    @Override
    public Request createRequest(Request request) {
        return requestRepo.save(request);
    }

    @Override
    public List<Request> getAllRequests(){ return requestRepo.findAll();}

    @Override
    public Request getRequestById(UUID id) {
        return requestRepo.findById(id).orElse(null);
    }

    @Override
    public Page<Request> getAllRequestsPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return requestRepo.findAll(pageable);
    }

    @Override
    public void updateRequest(Request request) {
        requestRepo.save(request);
    }

    @Override
    public void deleteRequest(UUID id) {
   requestRepo.deleteById(id);
    }
}
