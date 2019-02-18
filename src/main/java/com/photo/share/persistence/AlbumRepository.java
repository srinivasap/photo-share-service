package com.photo.share.persistence;

import com.photo.share.domain.Album;
import com.photo.share.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends CrudRepository<Album, String> {
    Album findByTitle(String title);
}
