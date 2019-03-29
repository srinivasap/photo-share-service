package com.photo.share.service;

import com.photo.share.domain.Photo;
import com.photo.share.domain.User;
import com.photo.share.exception.InvalidOperaiton;
import com.photo.share.exception.PhotoNotFoundException;
import com.photo.share.exception.UserAccountNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * Service layer to handle photo-share operations.
 */
public interface PhotoShareService {

    /**
     * Save photos to persistence layer under user album and associate tags if any.
     * @param user the user entity
     * @param album the album title
     * @param photos the photos to save
     * @param customTags the custom tags
     * @return the persisted photo entities
     * @throws UserAccountNotFoundException when user doesn't exists and auto-create is disabled
     */
    List<Photo> savePhotos(User user, String album, List<Photo> photos, Map<String, String> customTags) throws UserAccountNotFoundException;

    /**
     * Retrieve photo from the persistence store by uuid.
     * @param uuid the photo uuid
     * @return the persisted photo entity
     * @throws PhotoNotFoundException when photo not found by uuid
     */
    Photo getPhotoWithUUID(String uuid) throws PhotoNotFoundException;;

    /**
     * Delete photo from the peristence store by uuid
     * @param user the user entity
     * @param uuid the photo id
     * @throws UserAccountNotFoundException when user doesn't exists and auto-create is disabled
     * @throws PhotoNotFoundException when photo not found by id
     * @throws InvalidOperaiton when photo doesn't belong to user
     */
    void deletePhotoWithUUID(User user, String uuid) throws PhotoNotFoundException, InvalidOperaiton;

    /**
     * Search photos by tags
     * @param metadata the metadata search criteria
     * @return the photos for persistence layer matching search criteria
     */
    List<Photo> searchPhotosWithMetadata(Map<String, String> metadata);

    Photo likePhoto(String uuid, String userId) throws PhotoNotFoundException, InvalidOperaiton;

}
