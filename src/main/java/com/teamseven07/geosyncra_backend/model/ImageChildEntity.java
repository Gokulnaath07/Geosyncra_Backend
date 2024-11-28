package com.teamseven07.geosyncra_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="image_child")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageName;
    private String imageType;

    @Lob
    @JsonIgnore
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    @JsonBackReference
    private ImageEntity imageEntity;

}
