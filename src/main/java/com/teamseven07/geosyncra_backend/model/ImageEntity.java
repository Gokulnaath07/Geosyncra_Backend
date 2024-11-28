package com.teamseven07.geosyncra_backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="testimages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @NotNull
    private String location;

    @NotNull
    private String geoLocation;

    // One-to-many relationship with ImageChildEntity
    @OneToMany(mappedBy = "imageEntity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ImageChildEntity> imageChildren;
}
