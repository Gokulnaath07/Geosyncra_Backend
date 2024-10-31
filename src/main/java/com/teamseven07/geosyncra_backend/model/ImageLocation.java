package com.teamseven07.geosyncra_backend.model;


public class ImageLocation {
    private Integer id;
    private String geoLocation;

    public ImageLocation(Integer id, String geoLocation) {
        this.id = id;
        this.geoLocation = geoLocation;
    }

    public Integer getId() {
        return id;
    }

    public String getGeoLocation() {
        return geoLocation;
    }
}
