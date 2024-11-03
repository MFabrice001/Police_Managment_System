package com.PoliceManagementSystem.Controller;
import com.PoliceManagementSystem.Model.Rank;
import com.PoliceManagementSystem.Model.Signup;
import com.PoliceManagementSystem.Service.SignupService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;
import java.util.UUID;

@Controller
public class SignupController {

    @Autowired
    SignupService signupService;

    // Update the mapping to be more standardized
    @GetMapping("/police")
    public String showPoliceForm(Model model) {
        model.addAttribute("policeModel", new Signup());
        model.addAttribute("rank", Rank.values());
        return "police";
    }

    @PostMapping("/createPolice")
    public String createPolice(@ModelAttribute("policeModel") @Valid Signup signup, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Validation failed, return to the signup form with error messages
            model.addAttribute("policeModel", signup);
            model.addAttribute("rank", Rank.values());
            return "police"; // Adjust this to your signup form view name
        } else {
            // Validation passed, proceed to create the user
            signupService.createSignup(signup);
            // Sending email after creating police
            String recipient = signup.getEmail();
            String message = "<strong>Thank you for signing up.</strong><br>"
                    + "<strong>Rank:</strong> " + signup.getRank() + "<br>"
                    + "<strong>MilitaryNumber:</strong> " + signup.getPoliceNumber() + "<br>"
                    + "<strong>Username:</strong> " + signup.getUsername() + "<br>"
                    + "<strong>Password:</strong> " + signup.getPassword();
            // Call the SendMail method with recipient and message
            String redirectURL = SendMail(recipient, message);

            // Add success message attribute to display on the form
            model.addAttribute("successMessage", "User successfully inserted!");
            return "police"; // Adjust this to your signup form view name
        }
    }

    public String SendMail(String recipient, String messageBody) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        final String senderEmail = "kayiranga420@gmail.com";
        final String appPassword = "lkvx ungt plcy kwiw";

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(senderEmail));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            emailMessage.setSubject("This Email Is To Give You Login Info");

            // Create a MimeBodyPart to represent the message body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(messageBody, "text/html");

            // Create a Multipart object to hold the body parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Set the content of the message to be the multipart object
            emailMessage.setContent(multipart);

            // Send the message
            Transport.send(emailMessage);

            return "redirect:/"; // Adjust this to your desired redirect URL after sending email

        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }


//    @GetMapping("/list")
//    public String listOfUsers(Model model) {
//        model.addAttribute("allSignup", signupService.getAllSignup()); // Get all users
//        return "ListOfUser"; // Return list of users view
//    }

    @GetMapping("/police/delete_police/{id}")
    public String deleteSoldier(@PathVariable("id") UUID id){
        signupService.deleteSignup(id);
        return "redirect:/list";
 }
     // pagination
     @GetMapping("/list")
     public String report(
             @RequestParam(name = "page", defaultValue = "0") int currentPage,
             @RequestParam(name = "size", defaultValue = "4") int size,
             Model model) {
         // Fetch the page of police officers
         Page<Signup> signupPage = signupService.getAllPolicePageable(currentPage, size);
         model.addAttribute("allSignup", signupPage.getContent());

         // Add pagination-related attributes
         model.addAttribute("currentPagePolice", currentPage);
         model.addAttribute("totalPagesPolice", signupPage.getTotalPages());
         model.addAttribute("size", size);

         return "ListOfUser"; // Replace with your actual view name
     }

}




