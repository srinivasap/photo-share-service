package com.photo.share.service;

import com.photo.share.domain.Photo;
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
     * @param userId the user id
     * @param album the album title
     * @param photos the photos to save
     * @param customTags the custom tags
     * @return the persisted photo entities
     * @throws UserAccountNotFoundException when user doesn't exists and auto-create is disabled
     */
    List<Photo> savePhotos(String userId, String album, List<Photo> photos, Map<String, String> customTags) throws UserAccountNotFoundException;

    /**
     * Retrieve photo from the persistence store by uuid.
     * @param uuid the photo uuid
     * @return the persisted photo entity
     * @throws PhotoNotFoundException when photo not found by uuid
     */
    Photo getPhotoWithUUID(String uuid) throws PhotoNotFoundException;;

    /**
     * Delete photo from the peristence store by uuid
     * @param userId the user id
     * @param uuid the photo id
     * @throws UserAccountNotFoundException when user doesn't exists and auto-create is disabled
     * @throws PhotoNotFoundException when photo not found by id
     * @throws InvalidOperaiton when photo doesn't belong to user
     */
    void deletePhotoWithUUID(String userId, String uuid) throws UserAccountNotFoundException, PhotoNotFoundException, InvalidOperaiton;

    /**
     * Search photos by tags
     * @param metadata the metadata search criteria
     * @return the photos for persistence layer matching search criteria
     */
    List<Photo> searchPhotosWithMetadata(Map<String, String> metadata);

}
