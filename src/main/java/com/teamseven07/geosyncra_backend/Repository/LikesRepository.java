package com.teamseven07.geosyncra_backend.Repository;

import com.teamseven07.geosyncra_backend.model.ImageEntity;
import com.teamseven07.geosyncra_backend.model.ImageLikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<ImageLikesEntity, Integer> {
    List<ImageLikesEntity> findByImageEntityId(Integer imageEntityId);
    Optional<ImageLikesEntity> findByImageEntity(ImageEntity imageEntity);

}
