package com.teamseven07.geosyncra_backend.controller;

import com.teamseven07.geosyncra_backend.Repository.ImageRepository;
import com.teamseven07.geosyncra_backend.Service.ImageService;
import com.teamseven07.geosyncra_backend.model.ImageDto;
import com.teamseven07.geosyncra_backend.model.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500") 
public class ImageController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;



    @GetMapping("/images")
    public ResponseEntity<List<ImageEntity>> getAllImages() {
        return new ResponseEntity<>(imageService.getAllImages(), HttpStatus.OK);
    }



    @PostMapping("/uploadImages")
    public ResponseEntity<?> uploadImages(@RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam("location") String location,
                                          @RequestParam("geoLocation") String geoLocation,
                                          @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            ImageEntity savedImageEntity = imageService.addProduct(name, description, location, geoLocation, imageFile);
            return new ResponseEntity<>(savedImageEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/images/{imageid}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable("imageid") Integer imageId) {
        // Retrieve the ImageEntity from the database by ID
        Optional<ImageEntity> imageOptional = imageRepository.findById(imageId);

        if (imageOptional.isPresent()) {
            ImageEntity imageEntity = imageOptional.get();

            // Check that both imageType and imageData are not null
            if (imageEntity.getImageType() != null && imageEntity.getImageData() != null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(imageEntity.getImageType()))
                        .body(imageEntity.getImageData());
            } else {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/location/{location}")
    public ResponseEntity<List<ImageDto>> findByLocation(
            @PathVariable("location") String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        List<ImageDto> imageResponse = imageService.findByLocation(location, page, size);
        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }


    @GetMapping("images/{id}/geoLocation")
    public ResponseEntity<String> getGeoLocation(@PathVariable Integer id) {
        String geoLocation = imageService.getGeoLocation(id);
        return new ResponseEntity<>(geoLocation, HttpStatus.OK);
    }


}
