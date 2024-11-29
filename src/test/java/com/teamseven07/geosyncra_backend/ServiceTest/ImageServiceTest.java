package com.teamseven07.geosyncra_backend.ServiceTest;


import com.teamseven07.geosyncra_backend.Repository.CommentRepository;
import com.teamseven07.geosyncra_backend.Repository.ImageChildRepository;
import com.teamseven07.geosyncra_backend.Repository.ImageRepository;
import com.teamseven07.geosyncra_backend.Repository.LikesRepository;
import com.teamseven07.geosyncra_backend.Service.ImageService;
import com.teamseven07.geosyncra_backend.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageChildRepository imageChildRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private LikesRepository likesRepository;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testFindAllDtoPage() {
        // Prepare the page request
        Pageable pageable = PageRequest.of(0, 3);

        // Use setters to create ImageEntity objects
        ImageEntity imageEntity1 = new ImageEntity();
        imageEntity1.setId(1);
        imageEntity1.setName("Test Image 1");
        imageEntity1.setDescription("Description 1");
        imageEntity1.setLocation("Location 1");
        imageEntity1.setGeoLocation("GeoLocation 1");
        imageEntity1.setImageChildren(Collections.emptyList());  // Use empty list or mock accordingly

        ImageEntity imageEntity2 = new ImageEntity();
        imageEntity2.setId(2);
        imageEntity2.setName("Test Image 2");
        imageEntity2.setDescription("Description 2");
        imageEntity2.setLocation("Location 2");
        imageEntity2.setGeoLocation("GeoLocation 2");
        imageEntity2.setImageChildren(Collections.emptyList());

        ImageEntity imageEntity3 = new ImageEntity();
        imageEntity3.setId(3);
        imageEntity3.setName("Test Image 3");
        imageEntity3.setDescription("Description 3");
        imageEntity3.setLocation("Location 3");
        imageEntity3.setGeoLocation("GeoLocation 3");
        imageEntity3.setImageChildren(Collections.emptyList());

        // Mock Page of ImageEntity
        Page<ImageEntity> imagePage = new PageImpl<>(Arrays.asList(imageEntity1, imageEntity2, imageEntity3));

        // Mock the repository call
        when(imageRepository.findAll(pageable)).thenReturn(imagePage);

        // Call the service method
        Page<ImageAllDto> result = imageService.findAllDtoPage(0, 3);

        // Assertions
        assertEquals(3, result.getTotalElements());
        assertEquals("Test Image 1", result.getContent().get(0).getName());
        assertEquals("Description 2", result.getContent().get(1).getDescription());
        assertEquals("GeoLocation 3", result.getContent().get(2).getGeoLocation());

        // Print message to console if test passes
        System.out.println("Test passed: find all details like name, description, location...");
    }




    @Test
    void testFindByLocation() {
        String location = "test-location";
        Pageable pageable = PageRequest.of(0, 3);

        // Use the default constructor and setter methods
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(1); // Set the id
        imageEntity.setName("Test Image");
        imageEntity.setDescription("A test description");
        imageEntity.setLocation(location);
        imageEntity.setGeoLocation("GeoLocation");
        imageEntity.setImageChildren(Collections.emptyList());  // Initialize empty list or use a real one if needed

        Page<ImageEntity> imagePage = new PageImpl<>(Collections.singletonList(imageEntity));
        when(imageRepository.findByLocation(location, pageable)).thenReturn(imagePage);

        Page<ImageDto> imageDtos = imageService.findByLocation(location, 0, 3);
        assertEquals(1, imageDtos.getTotalElements());
        assertEquals(location, imageDtos.getContent().get(0).getLocation());
        // Print message to console if test passes
        System.out.println("Test passed: Test find by location...");
    }


    @Test
    void testAddProduct() throws IOException {
        // Assuming MultipartFile mock here
        MultipartFile imageFile = mock(MultipartFile.class);
        when(imageFile.getOriginalFilename()).thenReturn("test.jpg");
        when(imageFile.getContentType()).thenReturn("image/jpeg");
        when(imageFile.getBytes()).thenReturn(new byte[0]);

        // Use the default constructor and setter methods
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(1);
        imageEntity.setName("Test Product");
        imageEntity.setDescription("Description");
        imageEntity.setLocation("Test Location");
        imageEntity.setGeoLocation("GeoLocation");
        imageEntity.setImageChildren(null);  // or set an empty list if needed

        when(imageRepository.save(any(ImageEntity.class))).thenReturn(imageEntity);

        ImageEntity savedImage = imageService.addProduct("Test Product", "Description", "Test Location", "GeoLocation", imageFile);

        assertEquals("Test Product", savedImage.getName());
        assertEquals("Description", savedImage.getDescription());
        // Print message to console if test passes
        System.out.println("Test passed: To add products ....");
    }


//    @Test
//    void testAddChildImage() throws IOException {
//        Integer imageId = 1;
//        String imageName = "childImage.jpg";
//        MultipartFile imageFile = mock(MultipartFile.class);
//        when(imageFile.getOriginalFilename()).thenReturn(imageName);
//        when(imageFile.getContentType()).thenReturn("image/jpeg");
//        when(imageFile.getBytes()).thenReturn(new byte[0]);
//
//        ImageEntity parentImage = new ImageEntity();
//        parentImage.setId(imageId);
//        when(imageRepository.findById(imageId)).thenReturn(java.util.Optional.of(parentImage));
//
//        ImageChildEntity childImage = new ImageChildEntity();
//        when(imageChildRepository.save(any(ImageChildEntity.class))).thenReturn(childImage);
//
//        ImageChildEntity savedChild = imageService.addChildImage(imageId, imageName, imageFile);
//        assertNotNull(savedChild);
//        assertEquals(imageName, savedChild.getImageName());
//    }

    @Test
    void testAddComment() {
        Integer imageId = 1;
        String commentText = "Great Image!";
        ImageEntity imageEntity = new ImageEntity();
        when(imageRepository.findById(imageId)).thenReturn(java.util.Optional.of(imageEntity));

        ImageCommentChildEntity comment = new ImageCommentChildEntity();
        when(commentRepository.save(any(ImageCommentChildEntity.class))).thenReturn(comment);

        ImageCommentChildEntity savedComment = imageService.addComment(imageId, commentText);
        assertNotNull(savedComment);
        // Print message to console if test passes
        System.out.println("Test passed: Adding comments....");
    }

    @Test
    void testAddLikes() {
        Integer imageId = 1;
        Integer like = 1;
        ImageEntity imageEntity = new ImageEntity();
        when(imageRepository.findById(imageId)).thenReturn(java.util.Optional.of(imageEntity));

        ImageLikesEntity likesEntity = new ImageLikesEntity();
        when(likesRepository.findByImageEntity(imageEntity)).thenReturn(java.util.Optional.empty());
        when(likesRepository.save(any(ImageLikesEntity.class))).thenReturn(likesEntity);

        ImageLikesEntity savedLikes = imageService.addLikes(imageId, like);
        assertNotNull(savedLikes);
        // Print message to console if test passes
        System.out.println("Test passed: adding likes...");
    }

//    @Test
//    void testGetAllChildImagesByParentId() {
//        Integer parentId = 1;
//        ImageChildEntity childEntity = new ImageChildEntity();
//        childEntity.setImageName("childImage.jpg");
//
//        when(imageChildRepository.findByImageEntityId(parentId)).thenReturn(Collections.singletonList(childEntity));
//
//        List<ImagePictureDto> childImages = imageService.getAllChildImagesByParentId(parentId);
//        assertEquals(1, childImages.size());
//        assertEquals("childImage.jpg", childImages.get(0).getImageName());
//    }

    @Test
    void testShowComments() {
        Integer parentId = 1;
        ImageCommentChildEntity commentEntity = new ImageCommentChildEntity();
        commentEntity.setComment("Great image!");

        when(commentRepository.findByImageEntityId(parentId)).thenReturn(Collections.singletonList(commentEntity));

        List<ImageCommentDto> comments = imageService.showComments(parentId);
        assertEquals(1, comments.size());
        assertEquals("Great image!", comments.get(0).getComment());
        // Print message to console if test passes
        System.out.println("Test passed: Showing comments...");
    }

    @Test
    void testViewLikes() {
        Integer parentId = 1;
        ImageLikesEntity likesEntity = new ImageLikesEntity();
        likesEntity.setLikes(5);

        when(likesRepository.findByImageEntityId(parentId)).thenReturn(Collections.singletonList(likesEntity));

        List<ImageLikesDto> likes = imageService.viewLikes(parentId);
        assertEquals(1, likes.size());
        assertEquals(5, likes.get(0).getLikes());
        // Print message to console if test passes
        System.out.println("Test passed: To view likes...");
    }
}

