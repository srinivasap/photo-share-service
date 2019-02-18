package com.photo.share.persistence;

import com.photo.share.domain.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access model for Album entity type.
 */
@Repository
public interface AlbumRepository extends CrudRepository<Album, String> {
    Album findByTitle(String title);
}
