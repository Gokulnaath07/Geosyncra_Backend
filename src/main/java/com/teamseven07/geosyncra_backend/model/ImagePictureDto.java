package com.teamseven07.geosyncra_backend.model;

public class ImagePictureDto {
    private Integer id;
    private String name;
    private String image;

    private byte[] imageData;

    public ImagePictureDto(Integer id, String name, String image, byte[] imageData) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.imageData = imageData;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public byte[] getImageData() {
        return imageData;
    }

}
