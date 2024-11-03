package com.PoliceManagementSystem.Controller;

import com.PoliceManagementSystem.Model.Request;
import com.PoliceManagementSystem.Service.GunService;
import com.PoliceManagementSystem.Service.RequestService;
import com.PoliceManagementSystem.Service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class RequestController {
    @Autowired
    RequestService requestService;
    @Autowired
    GunService gunService;

    @Autowired
    SignupService signupService;


    @GetMapping("/request")
    public String request(Model model){
        model.addAttribute("requestModel", new Request());
        model.addAttribute("allGuns", gunService.getAllGuns());
        model.addAttribute("allSignup", signupService.getAllSignup());
        model.addAttribute("allRequests", requestService.getAllRequests());
        return "Assignedweapon";
    }

    @PostMapping("createRequest")
    public String createRequest(@ModelAttribute("requestModel") Request request){
        requestService.createRequest(request);
        return "redirect:/request";
    }

//
//    @GetMapping("/request/{id}/edit_request")
//    public String editGunForm(@PathVariable("id") UUID id, Model model) {
//        Request request = requestService.getRequestById(id);
//        model.addAttribute("request", request);
//        return "redirect:/list";
//    }

    @GetMapping("/request/request_list")
    public String listOfRequest(Model model) {
        // Your implementation
        model.addAttribute("allRequests", gunService.getAllGuns());
        return "Assignedweapon"; // or whatever the correct view name is
    }

//    @PostMapping("/{id}/edit_request")
//    public String editRequest(@PathVariable("id") UUID id, @ModelAttribute("request") Request request) {
//        request.setId(id);
//        requestService.updateRequest(request);
//        return "redirect:/request";
//    }


    @GetMapping("/request/delete_request/{id}")
    public String removeRequest(@PathVariable("id") UUID id) {
        // Your implementation
        gunService.deleteGun(id);
        return "redirect:/request";
    }
}
