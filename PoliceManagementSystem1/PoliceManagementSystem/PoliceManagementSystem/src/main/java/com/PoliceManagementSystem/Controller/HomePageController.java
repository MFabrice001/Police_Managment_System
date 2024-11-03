package com.PoliceManagementSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomePageController {


    @GetMapping("/")
    public String homepage(Model model) {
        return "homepage";
    }

//    @GetMapping("/error")
//    public String errorpage(Model model) {
//        return"error";
//}

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }
}
