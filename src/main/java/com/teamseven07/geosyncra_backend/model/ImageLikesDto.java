package com.teamseven07.geosyncra_backend.model;

public class ImageLikesDto {
    private Integer id;
    private Integer likes;

    public ImageLikesDto(Integer id, Integer likes) {
        this.id=id;
        this.likes=likes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
