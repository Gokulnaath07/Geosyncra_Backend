package com.teamseven07.geosyncra_backend.model;

public class ImageCommentDto {
    private Integer id;
    private String comment;

    public ImageCommentDto(Integer id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
