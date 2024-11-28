package com.teamseven07.geosyncra_backend.Service;

import com.teamseven07.geosyncra_backend.Repository.CommentRepository;
import com.teamseven07.geosyncra_backend.Repository.ImageChildRepository;
import com.teamseven07.geosyncra_backend.Repository.ImageRepository;
import com.teamseven07.geosyncra_backend.Repository.LikesRepository;
import com.teamseven07.geosyncra_backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageChildRepository imageChildRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikesRepository likesRepository;

    public ImageEntity addProduct(String name, String description, String location, String geoLocation, MultipartFile imageFile) throws IOException {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(name);
        imageEntity.setDescription(description);
        imageEntity.setLocation(location);
        imageEntity.setGeoLocation(geoLocation);

        // Create ImageChildEntity and set its image data
        ImageChildEntity imageChildEntity = new ImageChildEntity();
        imageChildEntity.setImageName(imageFile.getOriginalFilename());
        imageChildEntity.setImageType(imageFile.getContentType());
        imageChildEntity.setImageData(imageFile.getBytes());
        imageChildEntity.setImageEntity(imageEntity); // Linking the image child to parent

        // Save the image child in the database
        imageEntity.setImageChildren(List.of(imageChildEntity));

        return imageRepository.save(imageEntity);
    }

    @Transactional
    public ImageChildEntity addChildImage(Integer imageId, String imageName, MultipartFile imageFile) throws IOException {
        // Find the parent image by ID
        Optional<ImageEntity> imageEntityOptional = imageRepository.findById(imageId);
        if (imageEntityOptional.isPresent()) {
            ImageEntity parentImage = imageEntityOptional.get();

            // Create and save the child image
            ImageChildEntity childImage = new ImageChildEntity();
            childImage.setImageName(imageName);
            childImage.setImageData(imageFile.getBytes());
            childImage.setImageType(imageFile.getContentType());
            childImage.setImageEntity(parentImage);
            return imageChildRepository.save(childImage);
        }
        throw new RuntimeException("Parent image not found.");
    }
    @Transactional
    public ImageCommentChildEntity addComment(Integer id, String comment) {
        Optional<ImageEntity> image = imageRepository.findById(id);
        if(image.isPresent()){
            ImageEntity imageEntity = image.get();
            ImageCommentChildEntity imageCommentChildEntity = new ImageCommentChildEntity();
            imageCommentChildEntity.setComment(comment);
            imageCommentChildEntity.setImageEntity(imageEntity);
            return commentRepository.save(imageCommentChildEntity);
        }
        throw new RuntimeException("Image not found");
    }
    @Transactional
    public ImageLikesEntity addLikes(Integer id, Integer like) {
        Optional<ImageEntity> image = imageRepository.findById(id);
        if (image.isPresent()) {
            ImageEntity parentImage = image.get();

            // Check if there's already a like entry for the image
            Optional<ImageLikesEntity> existingLikes = likesRepository.findByImageEntity(parentImage);

            ImageLikesEntity saveLikes;
            if (existingLikes.isPresent()) {
                // If the like entry exists, update the like value
                saveLikes = existingLikes.get();
                saveLikes.setLikes(like);  // Update the like value
            } else {
                // If no entry exists, create a new one
                saveLikes = new ImageLikesEntity();
                saveLikes.setLikes(like);
                saveLikes.setImageEntity(parentImage);
            }

            // Save the like entry (either updated or new)
            return likesRepository.save(saveLikes);
        }
        throw new RuntimeException("Image not found");
    }



    @Transactional
    public Page<ImageDto> findByLocation(String location, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ImageEntity> imagePage = imageRepository.findByLocation(location, pageable);

        // Map ImageEntity to ImageDto
        return imagePage.map(image ->
                new ImageDto(image.getId(), image.getName(), image.getLocation(), image.getDescription()));
    }

    @Transactional
    public Page<ImageAllDto> findAllDtoPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ImageEntity> imagePage = imageRepository.findAll(pageable);

        return imagePage.map(image ->
                new ImageAllDto(image.getId(), image.getName(), image.getLocation(), image.getDescription(), image.getGeoLocation())
        );
    }

    @Transactional
    public List<ImagePictureDto> getAllChildImagesByParentId(Integer parentId) {
        List<ImageChildEntity> childImages = imageChildRepository.findByImageEntityId(parentId);
        return childImages.stream()
                .map(child -> new ImagePictureDto(
                        child.getId(),
                        child.getImageName(),
                        child.getImageType(),
                        child.getImageData()))
                .collect(Collectors.toList());
    }


    public String getGeoLocation(Integer id) throws IOException{
        Optional<ImageEntity> geoLocation = imageRepository.findById(id);
        if(geoLocation.isPresent()){
            return geoLocation.get().getGeoLocation();
        }
        throw new RuntimeException("Geolocation not found because there is no id in the database");
    }


    public List<ImageCommentDto> showComments(Integer parentId) {
        List<ImageCommentChildEntity> comment = commentRepository.findByImageEntityId(parentId);
        return comment.stream().map(comments-> new ImageCommentDto(comments.getId(),comments.getComment())).collect(Collectors.toList());
    }

    public List<ImageLikesDto> viewLikes(Integer parentId) {
        List<ImageLikesEntity> like = likesRepository.findByImageEntityId(parentId);
        return like.stream().map(likes->new ImageLikesDto(likes.getId(),likes.getLikes())).collect(Collectors.toList());
    }
}
