package com.police.management.police_management_system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create the directory for file storage.", e);
        }
    }

    public String storeFile(MultipartFile file) {
        // Validate the file
        if (file.isEmpty()) {
            throw new RuntimeException("Cannot store empty file.");
        }

        try {
            // Define the file storage path
            Path targetLocation = this.fileStorageLocation.resolve(file.getOriginalFilename());
            // Use Files.copy to write the file to the target location
            Files.copy(file.getInputStream(), targetLocation);

            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Please try again!", e);
        }
    }

    public Path loadFile(String filename) {
        // Normalize the filename to prevent directory traversal attacks
        return fileStorageLocation.resolve(filename).normalize();
    }
}
