package com.police.management.police_management_system.controller;

import com.police.management.police_management_system.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // File validation example (adjust limits as needed)
        if (file.isEmpty()) {
            logger.warn("Attempted to upload an empty file");
            return ResponseEntity.badRequest().body("Cannot upload empty file");
        }
        if (file.getSize() > 5 * 1024 * 1024) { // 5 MB limit
            logger.warn("Attempted to upload file exceeding size limit: {}", file.getSize());
            return ResponseEntity.badRequest().body("File size exceeds limit of 5MB");
        }

        try {
            String filename = fileStorageService.storeFile(file);
            logger.info("File uploaded successfully: {}", filename);
            return ResponseEntity.ok("File uploaded successfully: " + filename);
        } catch (Exception e) {
            logger.error("Failed to upload file", e);
            return ResponseEntity.internalServerError().body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path filePath = fileStorageService.loadFile(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                logger.info("File download initiated for: {}", filename);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                logger.warn("Requested file not found or is not readable: {}", filename);
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            logger.error("Failed to download file: {}", filename, e);
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
