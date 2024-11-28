package com.teamseven07.geosyncra_backend.Repository;


import com.teamseven07.geosyncra_backend.model.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

    Page<ImageEntity> findAll(Pageable pageable);
    Page<ImageEntity> findByLocation(String location, Pageable pageable);
}