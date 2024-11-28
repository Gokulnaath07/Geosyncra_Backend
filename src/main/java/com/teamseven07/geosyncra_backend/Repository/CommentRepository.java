package com.teamseven07.geosyncra_backend.Repository;

import com.teamseven07.geosyncra_backend.model.ImageCommentChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<ImageCommentChildEntity, Integer> {
    List<ImageCommentChildEntity> findByImageEntityId(Integer imageEntityId);
}
