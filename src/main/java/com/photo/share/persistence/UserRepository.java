package com.photo.share.persistence;

import com.photo.share.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access model for User entity type.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
