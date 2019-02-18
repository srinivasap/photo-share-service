package com.photo.share.service;

import com.photo.share.domain.Photo;
import com.photo.share.domain.User;
import com.photo.share.exception.InvalidOperaiton;
import com.photo.share.exception.PhotoNotFoundException;
import com.photo.share.exception.UserAccountNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PhotoShareService {

    List<Photo> savePhotos(String userId, String album, List<Photo> photos, Map<String, String> customTags) throws UserAccountNotFoundException;

    Photo getPhotoWithUUID(String uuid) throws PhotoNotFoundException;;

    void deletePhotoWithUUID(String userId, String uuid) throws UserAccountNotFoundException, PhotoNotFoundException, InvalidOperaiton;

    List<Photo> searchPhotosWithMetadata(Map<String, String> metadata);

}
