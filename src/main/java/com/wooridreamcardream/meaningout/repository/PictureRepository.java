package com.wooridreamcardream.meaningout.repository;

import com.wooridreamcardream.meaningout.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long>{
    List<Picture> findByCategoryId(Long categoryId);
    Optional<Picture> findByCategoryIdAndImageUrl(Long categoryId, String imageUrl);
}
