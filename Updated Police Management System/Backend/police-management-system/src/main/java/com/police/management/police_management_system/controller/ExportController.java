package com.police.management.police_management_system.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.police.management.police_management_system.entity.User;
import com.police.management.police_management_system.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/export")
public class ExportController {

    private static final Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    private UserRepository userRepository;

    // Export to Excel
    @GetMapping("/excel")
    public ResponseEntity<byte[]> exportToExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            List<User> users = userRepository.findAll();
            Sheet sheet = workbook.createSheet("Users");

            writeHeader(sheet.createRow(0));

            int rowIndex = 1;
            for (User user : users) {
                writeDataRow(sheet.createRow(rowIndex++), user);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.xlsx")
                    .body(out.toByteArray());
        } catch (IOException e) {
            logger.error("Error occurred while exporting data to Excel", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Export to CSV
    @GetMapping("/csv")
    public ResponseEntity<byte[]> exportToCSV() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(out))) {

            List<User> users = userRepository.findAll();
            writer.writeNext(new String[]{"ID", "Username", "Email"});

            for (User user : users) {
                writer.writeNext(new String[]{user.getId().toString(), user.getUsername(), user.getEmail()});
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.csv")
                    .body(out.toByteArray());
        } catch (IOException e) {
            logger.error("Error occurred while exporting data to CSV", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Export to PDF
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportToPDF() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            List<User> users = userRepository.findAll();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("User List", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD)));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.addCell("ID");
            table.addCell("Username");
            table.addCell("Email");

            for (User user : users) {
                table.addCell(user.getId().toString());
                table.addCell(user.getUsername());
                table.addCell(user.getEmail());
            }

            document.add(table);
            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.pdf")
                    .body(out.toByteArray());
        } catch (DocumentException | IOException e) {
            logger.error("Error occurred while exporting data to PDF", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Import from Excel
    @Transactional
    @PostMapping("/import/excel")
    public ResponseEntity<String> importFromExcel(@RequestParam("file") InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                String username = row.getCell(1).getStringCellValue();
                String email = row.getCell(2).getStringCellValue();

                if (username == null || username.isEmpty() || email == null || email.isEmpty()) {
                    logger.warn("Skipping row with missing data at row number {}", row.getRowNum());
                    continue;
                }

                User user = userRepository.findByEmail(email);
                if (user != null) {
                    user.setUsername(username);
                    logger.info("Updating existing user with email {}", email);
                } else {
                    user = new User((String) null, username, email);
                }
                userRepository.save(user);
            }

            return ResponseEntity.ok("Data imported successfully from Excel.");
        } catch (IOException e) {
            logger.error("Error occurred while importing data from Excel", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import data.");
        }
    }

    // Import from CSV
    @Transactional
    @PostMapping("/import/csv")
    public ResponseEntity<String> importFromCSV(@RequestParam("file") InputStream inputStream) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] fields = line.split(",");
                if (fields.length < 3) continue;

                Long id = Long.parseLong(fields[0]);
                String username = fields[1];
                String email = fields[2];

                if (username.isEmpty() || email.isEmpty()) {
                    logger.warn("Skipping row with missing data: {}", line);
                    continue;
                }

                User user = userRepository.findByEmail(email);
                if (user != null) {
                    user.setUsername(username);
                } else {
                    user = new User(id, username, email);
                }
                userRepository.save(user);
            }

            return ResponseEntity.ok("Data imported successfully from CSV.");
        } catch (IOException e) {
            logger.error("Error occurred while importing data from CSV", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import data.");
        }
    }

    // Utility method for writing headers in Excel
    private void writeHeader(Row header) {
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Username");
        header.createCell(2).setCellValue("Email");
    }

    // Utility method for writing data rows in Excel
    private void writeDataRow(Row row, User user) {
        row.createCell(0).setCellValue(user.getId());
        row.createCell(1).setCellValue(user.getUsername());
        row.createCell(2).setCellValue(user.getEmail());
    }
}
