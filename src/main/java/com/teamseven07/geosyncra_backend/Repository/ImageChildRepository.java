package com.teamseven07.geosyncra_backend.Repository;

import com.teamseven07.geosyncra_backend.model.ImageChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ImageChildRepository extends JpaRepository<ImageChildEntity, Integer> {

    List<ImageChildEntity> findByImageEntityId(Integer imageEntityId);
}
