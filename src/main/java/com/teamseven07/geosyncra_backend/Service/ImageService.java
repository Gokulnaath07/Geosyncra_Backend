package com.teamseven07.geosyncra_backend.Service;

import com.teamseven07.geosyncra_backend.Repository.ImageRepository;
import com.teamseven07.geosyncra_backend.model.ImageDto;
import com.teamseven07.geosyncra_backend.model.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<ImageEntity> getAllImages() {
        return imageRepository.findAll();
    }



    public ImageEntity addProduct(String name, String description, String location, MultipartFile imageFile) throws IOException {

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(name);
        imageEntity.setDescription(description);
        imageEntity.setLocation(location);
        imageEntity.setImageName(imageFile.getOriginalFilename());
        imageEntity.setImageType(imageFile.getContentType());
        imageEntity.setImageData(imageFile.getBytes());

        return imageRepository.save(imageEntity);
    }


    @Transactional
    public List<ImageDto> findByLocation(String location, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ImageEntity> imagePage = imageRepository.findByLocation(location, pageable);

        // Map ImageEntity to ImageDto
        return imagePage.stream()
                .map(image -> new ImageDto(image.getName(), image.getLocation(), image.getDescription()))
                .collect(Collectors.toList());
    }

}
