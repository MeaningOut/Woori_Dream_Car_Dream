package com.wooridreamcardream.meaningout.repository;

import com.wooridreamcardream.meaningout.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long>{
    List<Picture> findByCategoryId(Long categoryId);
}
