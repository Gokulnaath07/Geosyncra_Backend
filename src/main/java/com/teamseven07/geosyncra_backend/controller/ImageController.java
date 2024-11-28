package com.teamseven07.geosyncra_backend.controller;

import com.teamseven07.geosyncra_backend.Repository.ImageChildRepository;
import com.teamseven07.geosyncra_backend.Repository.ImageRepository;
import com.teamseven07.geosyncra_backend.Service.ImageService;
import com.teamseven07.geosyncra_backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageChildRepository imageChildRepository;


    @GetMapping("/images")
    public ResponseEntity<Page<ImageAllDto>> getAllImages(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(imageService.findAllDtoPage(page, size), HttpStatus.OK);
    }


    @GetMapping("/images/{imageId}/data")
    public ResponseEntity<byte[]> getImageDataById(@PathVariable Integer imageId) {
        Optional<ImageChildEntity> imageChildEntityOptional = imageChildRepository.findById(imageId);

        if (imageChildEntityOptional.isPresent()) {
            ImageChildEntity imageChildEntity = imageChildEntityOptional.get();

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(imageChildEntity.getImageType()))
                    .body(imageChildEntity.getImageData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/{parentId}/child")
    public ResponseEntity<List<ImagePictureDto>> getChildImagesByParentId(@PathVariable Integer parentId) {
        List<ImagePictureDto> childImages = imageService.getAllChildImagesByParentId(parentId);
        return ResponseEntity.ok(childImages);
    }


    @PostMapping("/uploadImages")
    public ResponseEntity<?> uploadImages(@RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam("location") String location,
                                          @RequestParam("geoLocation") String geoLocation,
                                          @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            ImageEntity savedImageEntity = imageService.addProduct(name, description, location.toLowerCase(), geoLocation, imageFile);
            return new ResponseEntity<>(savedImageEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/{imageId}/addChildImage")
    public ResponseEntity<?> addChildImage(@PathVariable Integer imageId,
                                           @RequestParam("imageName") String imageName,
                                           @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            ImageChildEntity savedChildImage = imageService.addChildImage(imageId, imageName, imageFile);
            return new ResponseEntity<>(savedChildImage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/location/{location}")
    public ResponseEntity<Page<ImageDto>> findByLocation(
            @PathVariable("location") String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Page<ImageDto> imageResponse = imageService.findByLocation(location, page, size);
        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }


    @GetMapping("images/{id}/geoLocation")
    public ResponseEntity<String> getGeoLocation(@PathVariable Integer id) throws IOException {
        // Call the service method to fetch the geoLocation by id
        String geoLocation = imageService.getGeoLocation(id);

        // Return the geoLocation in the response with HTTP status OK (200)
        return new ResponseEntity<>(geoLocation, HttpStatus.OK);
    }
    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addComment(@PathVariable Integer id, @RequestParam("comments") String comment) {
        try{
            ImageCommentChildEntity savedComment = imageService.addComment(id, comment);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/like")
    ResponseEntity<?>addLikes(@PathVariable Integer id,
                              @RequestParam("like") Integer like){
        try{
            ImageLikesEntity savedLikes=imageService.addLikes(id, like);
            return new ResponseEntity<>(savedLikes, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("images/{id}/comment")
    ResponseEntity<List<ImageCommentDto>> getComments(@PathVariable Integer id){
        List<ImageCommentDto> showComments=imageService.showComments(id);
        return new ResponseEntity<>(showComments,HttpStatus.OK);
    }

    @GetMapping("images/{id}/likes")
    ResponseEntity<List<ImageLikesDto>> viewLikes(@PathVariable Integer id){
        List<ImageLikesDto> viewLikes=imageService.viewLikes(id);
        return new ResponseEntity<>(viewLikes,HttpStatus.OK);
    }

}
