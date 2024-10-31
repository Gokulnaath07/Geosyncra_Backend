package com.teamseven07.geosyncra_backend.controller;

import com.teamseven07.geosyncra_backend.Repository.ImageRepository;
import com.teamseven07.geosyncra_backend.model.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageControllerforsave {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/images/{id}/save")
    public ResponseEntity<String> saveImageLocally(@PathVariable Integer id) {
        // Step 2: Retrieve the image from the database
        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Step 3: Save the image data as a file locally
        try {
            // Define the file path and name where the image will be saved
            Path imagePath = Paths.get("C:/images/", imageEntity.getImageName());
            Files.createDirectories(imagePath.getParent()); // Create the directory if it doesn’t exist

            try (FileOutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                outputStream.write(imageEntity.getImageData());
            }

            return ResponseEntity.ok("Image saved successfully at: " + imagePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving image: " + e.getMessage());
        }
    }

}
