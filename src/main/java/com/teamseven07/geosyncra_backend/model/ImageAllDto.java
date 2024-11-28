package com.teamseven07.geosyncra_backend.model;

public class ImageAllDto {

    private Integer id;
    private String name;
    private String location;
    private String description;
    private String geoLocation;
//    @Lob
//    private byte[] imageData;

    public ImageAllDto(Integer id,String name, String location, String description, String geoLocation) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.geoLocation = geoLocation;
//        this.imageData = imageData;
    }
//    public byte[] getImageData() {
//        return imageData;
//    }

//    public Integer getId() {
//        return id;
//    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getGeoLocation() {
        return geoLocation;
    }
}
