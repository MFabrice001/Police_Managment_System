package com.PoliceManagementSystem.Controller;

import com.PoliceManagementSystem.Model.Gun;
import com.PoliceManagementSystem.Model.Guntype;
import com.PoliceManagementSystem.Model.Manufacturer;
import com.PoliceManagementSystem.Service.GunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class GunController {

    @Autowired
    private GunService gunService;

    @GetMapping("/gun")
    public String gun(Model model) {
        // ... (existing code)
        model.addAttribute("gunModel", new Gun());
        model.addAttribute("gunType", Guntype.values());
        model.addAttribute("manufacturer", Manufacturer.values());
        model.addAttribute("allGuns", gunService.getAllGuns());
        return "Gun";

    }

    @PostMapping("/createGun")
    public String createGun(@ModelAttribute("gunModel") Gun gun) {
        gunService.createGun(gun);
        return "redirect:/gun";
    }

    @GetMapping("/{id}/edit_gun")
    public String updateGunForm(@PathVariable("id") UUID id, Model model) {
        Gun gun = gunService.getGunById(id);
        model.addAttribute("gun", gun);
        model.addAttribute("gunType", Guntype.values());
        model.addAttribute("manufacturer", Manufacturer.values());
        model.addAttribute("allGuns", gunService.getAllGuns());
        return "Gun"; // Change to the appropriate Thymeleaf template name if needed
    }

//    @PostMapping("/{id}/edit_gun")
//    public String editGun(@PathVariable("id") UUID id, @ModelAttribute("gun") Gun gun) {
//        gun.setId(id); // Set the ID for update
//        gunService.updateGun(gun);
//        return "redirect:/gun";
//    }


    @GetMapping("/police/delete_dun/{id}")
    public String deleteSoldier(@PathVariable("id") UUID id,Model model){
        try {
        gunService.deleteGun(id);
        return "redirect:/gun";
    } catch (DataIntegrityViolationException e) {
        // Log the exception if needed
        e.printStackTrace();

        // Add custom error message to the model
        model.addAttribute("errorMessage", "Cannot delete Gun due to existing dependencies.");

        // Return the error view
        return "error"; // Create an "error.html" Thymeleaf template for displaying error messages
    }
    }
//    @RequestMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
//    @ResponseBody
//    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) {
//        Optional<Gun> gunOptional = gunService.;
//        byte[] imageBytes = meeting.get().getEquipmentPhoto();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        headers.setContentLength(imageBytes.length);
//        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//}
}