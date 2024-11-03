package com.PoliceManagementSystem.Controller;

import com.PoliceManagementSystem.Model.Returns;
import com.PoliceManagementSystem.Service.RequestService;
import com.PoliceManagementSystem.Service.ReturnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class ReturnsController {

    @Autowired
    ReturnsService returnsService;
    @Autowired
    RequestService requestService;

    @GetMapping("/returns")
    public String returns(Model model){
        model.addAttribute("returnsModel", new Returns());
        model.addAttribute("allRequests", requestService.getAllRequests());
        model.addAttribute("allReturns", returnsService.getAllReturn());
        return "Returnweapon";
    }

    @PostMapping("/createReturns")
    public String createReturn(@ModelAttribute("returnsModel") Returns returns){
        returnsService.createReturns(returns);
        return "redirect:/returns";
    }

//    @GetMapping("/returns/{id}/edit_return")
//    public String editReturnForm(@PathVariable("id") UUID id, Model model) {
//        Returns returns = returnsService.getReturnById(id);
//        model.addAttribute("returns", returns);
//        model.addAttribute("allRequests", requestService.getAllRequests());
//        return "editReturn"; // Assuming you have an editReturn.html template
//    }

//    @PostMapping("/{id}/edit_return")
//    public String editReturn(@PathVariable("id") UUID id, @ModelAttribute("returns") Returns returns) {
//        returns.setId(id);
//        returnsService.updateReturn(returns);
//        return "redirect:/returns";
//    }

    @GetMapping("/returns/delete_return/{id}")
    public String removeReturn(@PathVariable("id") UUID id) {
        returnsService.deleteReturn(id);
        return "redirect:/returns";
    }
}

