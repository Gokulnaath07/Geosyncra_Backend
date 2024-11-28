package com.teamseven07.geosyncra_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="image_child_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCommentChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String comment;


    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    @JsonBackReference
    private ImageEntity imageEntity;

}