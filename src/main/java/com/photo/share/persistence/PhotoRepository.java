package com.photo.share.persistence;

import com.photo.share.domain.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access model for Photo entity type.
 */
@Repository
public interface PhotoRepository extends PagingAndSortingRepository<Photo, String> {

    @Query("SELECT p FROM Photo p, MetaData m WHERE p.metaData = m AND m.tags LIKE %:tag%")
    List<Photo> findAllByTag(@Param("tag") String tag);

}
