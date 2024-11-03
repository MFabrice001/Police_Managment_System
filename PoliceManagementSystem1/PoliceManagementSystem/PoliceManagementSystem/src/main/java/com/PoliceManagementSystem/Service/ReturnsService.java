package com.PoliceManagementSystem.Service;



import com.PoliceManagementSystem.Model.Returns;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ReturnsService {
    Returns createReturns(Returns returns);

    List<Returns> getAllReturn();
    Returns getReturnById(UUID id);
    Page<Returns> getAllReturnsPageable(int page, int size);

    void updateReturn(Returns returns);
    void deleteReturn(UUID id);

}
