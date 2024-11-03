    package com.PoliceManagementSystem.Controller;

    import com.opencsv.CSVWriter;
    import com.PoliceManagementSystem.Model.Gun;
    import com.PoliceManagementSystem.Model.Request;
    import com.PoliceManagementSystem.Model.Signup;
    import com.PoliceManagementSystem.Service.SignupService;
    import org.springframework.beans.factory.annotation.Autowired;

    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;

    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestParam;

    import java.io.ByteArrayOutputStream;
    import java.io.OutputStreamWriter;
    import java.nio.charset.StandardCharsets;
    import java.util.List;
    import java.util.stream.Collectors;


    @Controller
    public class SearchController {

        @Autowired
        SignupService signupService;

        @GetMapping("/search")
        public String search(@RequestParam(name = "policeNumber", required = false) Integer policeNumber, Model model) {
            model.addAttribute("allpolice", signupService.getAllSignup());

            // Check if policeNumber is present in the request
            if (policeNumber != null) {
                Signup foundPolice = signupService.findSignupByPoliceNumber(policeNumber);

                // Check if police is found`
                if (foundPolice != null) {
                    model.addAttribute("policeFound", true);
                    model.addAttribute("police", foundPolice);

                    // Retrieve requests and guns for the police
                    List<Request> policeRequests = foundPolice.getRequests();
                    List<Gun> policeGuns = policeRequests.stream().map(Request::getGun).collect(Collectors.toList());

                    model.addAttribute("policeRequests", policeRequests);
                    model.addAttribute("policeGuns", policeGuns);

                    // Add suggestedPoliceNumbers to the model
                    List<String> suggestedPoliceNumbers = getSuggestedPoliceNumbers(); // Implement this method
                    model.addAttribute("suggestedPoliceNumbers", suggestedPoliceNumbers);
                } else {
                    model.addAttribute("policeFound", false);
                }
            } else {
                // Handle the case when policeNumber is not present in the request
                model.addAttribute("policeFound", false);
            }

            return "search";
        }

        // Implement this method to provide suggested police numbers
        private List<String> getSuggestedPoliceNumbers() {
            // Add your logic to fetch suggested police numbers
            // For example, you can query them from the database or provide a predefined list
            // Return a List<String> of suggested police numbers
            return List.of("123", "456", "789");
        }
        @PostMapping("/download/allData")
        public ResponseEntity<byte[]> downloadAllData(@RequestParam(name = "policeNumber") Integer policeNumber) {
            System.out.println("Inside downloadAllData method");
            Signup foundPolice = null;

            // Check if policeNumber is present in the request
            if (policeNumber != null) {
                foundPolice = signupService.findSignupByPoliceNumber(policeNumber);
            }

            // Check if police is found
            if (foundPolice != null) {
                // Generate CSV data for Police
                byte[] policeData = generatePoliceDataAsCSV(foundPolice);

                // Generate CSV data for Request
                List<Request> policeRequests = foundPolice.getRequests();
                byte[] requestData = generateRequestDataAsCSV(policeRequests);

                // Generate CSV data for Gun
                List<Gun> policeGuns = policeRequests.stream()
                        .map(Request::getGun)
                        .collect(Collectors.toList());
                byte[] gunData = generateGunDataAsCSV(policeGuns);

                // Combine data into a single byte array
                byte[] allData = combineByteArrays(policeData, requestData, gunData);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "all_data.csv"); // Set filename

                System.out.println("Download data generated successfully");
                return new ResponseEntity<>(allData, headers, HttpStatus.OK);
            } else {
                // Police not found, handle accordingly (e.g., return an error response)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new byte[0]);
            }
        }

        private byte[] generatePoliceDataAsCSV(Signup signup) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {

                // Add header with formatting
                String[] header = {"Rank", "UserName", "PoliceNumber", "Email"};
                csvWriter.writeNext(getFormattedRow(header));

                // Add data row for the police
                String[] row = {
                        signup.getRank().toString(),
                        signup.getUsername(),
                        String.valueOf(signup.getPoliceNumber()),
                        signup.getEmail()
                };
                csvWriter.writeNext(row);

                csvWriter.flush();
                return outputStream.toByteArray();
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
                return new byte[0];
            }
        }

        private byte[] generateRequestDataAsCSV(List<Request> requests) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {

                // Add header with formatting
                String[] header = {"Gun", "RequestDate"};
                csvWriter.writeNext(getFormattedRow(header));

                // Add data rows for requests
                for (Request request : requests) {
                    String[] row = {request.getGun().getModel(), String.valueOf(request.getRequestdate())};
                    csvWriter.writeNext(row);
                }

                csvWriter.flush();
                return outputStream.toByteArray();
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
                return new byte[0];
            }
        }


        private byte[] generateGunDataAsCSV(List<Gun> guns) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {

                // Add header with formatting
                String[] header = {"Manufacturer", "Model", "GunType"};
                csvWriter.writeNext(getFormattedRow(header));

                // Add data rows for guns
                for (Gun gun : guns) {
                    String[] row = {gun.getManufacturer(), gun.getModel(), gun.getGuntype().toString()};
                    csvWriter.writeNext(row);
                }

                csvWriter.flush();
                return outputStream.toByteArray();
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
                return new byte[0];
            }
        }

        private String[] getFormattedRow(String[] row) {
            for (int i = 0; i < row.length; i++) {
                row[i] = "\"" + row[i] + "\"";
            }
            return row;
        }


        private byte[] combineByteArrays(byte[]... arrays) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                for (byte[] array : arrays) {
                    outputStream.write(array);
                }
                return outputStream.toByteArray();
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
                return new byte[0];
            }
        }
    }

