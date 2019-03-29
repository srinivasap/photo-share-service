package com.photo.share.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.photo.share.domain.Album;
import com.photo.share.domain.MetaData;
import com.photo.share.domain.Photo;
import com.photo.share.domain.User;
import com.photo.share.exception.InvalidOperaiton;
import com.photo.share.exception.PhotoNotFoundException;
import com.photo.share.exception.UserAccountNotFoundException;
import com.photo.share.persistence.AlbumRepository;
import com.photo.share.persistence.PhotoRepository;
import com.photo.share.persistence.UserRepository;
import com.photo.share.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * Implementation of PhotoShareService.
 */
@Service
@ConfigurationProperties(prefix = "photo")
public class PhotoShareServiceImpl implements PhotoShareService {

    private static final Logger LOG = LoggerFactory.getLogger(PhotoShareServiceImpl.class);

    @Value("${photo.share.host:http://localhost:8080}")
    private String host;

    @Value("${photo.share.context:/photos/}")
    private String context;

    @Value("${photo.share.auto_create_user_account:true}")
    private Boolean isAutoCreateUserAccountIfNotExists;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Photo> savePhotos(User user, String album, List<Photo> photos, Map<String, String> customTags) throws UserAccountNotFoundException {
        List<Photo> savedPhotos = new ArrayList<>();

        User userE = userAlbumManagement(user, album);
        for (Photo photo : photos) {
            Photo photoE = savePhoto(user, album, photo, customTags);
            updateDownloadUrl(photoE);
            savedPhotos.add(photoE);
        }

        return savedPhotos;
    }

    private Photo savePhoto(User user, String album, Photo photo, Map<String, String> customTags) throws UserAccountNotFoundException {
        photo.setOwner(user);
        photo.setUploadedDate(new Date());
        photo.addToAlbum(album);
        photo.setMetaData(extractMetadata(photo, customTags));
        return photoRepository.save(photo);
    }

    private MetaData extractMetadata(Photo photo, Map<String, String> customMetadata) {
        MetaData photoMetaData = new MetaData(customMetadata.get(Constants.TAGS));
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(photo.getData()));
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if (Constants.IMAGE_HEIGHT.equals(tag.getTagName())) {
                        photoMetaData.setImageHeight(tag.getDescription());
                    } else if (Constants.IMAGE_WIDTH.equals(tag.getTagName())) {
                        photoMetaData.setImageWidth(tag.getDescription());
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error while auto-generating meta-data for image "+photo.getUuid(), e);
        }
        return photoMetaData;
    }

    @Override
    public Photo getPhotoWithUUID(String uuid) throws PhotoNotFoundException {
        Photo entity = photoRepository.findById(uuid).orElse(null);
        if (entity == null)
            throw new PhotoNotFoundException("Found no photo with id "+ uuid);
        return entity;
    }

    @Override
    public void deletePhotoWithUUID(User user, String uuid) throws PhotoNotFoundException, InvalidOperaiton {
        Photo photo = photoRepository.findById(uuid).orElse(null);
        if (photo == null)
            throw new PhotoNotFoundException("Can't delete a photo that doesn't exist "+ uuid);

        if (photo.getOwner() != user)
            throw new InvalidOperaiton("Photo "+ uuid +" doesn't belong to user "+ user.getUserId() +" to delete.");

        photoRepository.delete(photo);
    }

    @Override
    public List<Photo> searchPhotosWithMetadata(Map<String, String> metadata) {
        if (metadata == null || metadata.isEmpty() || metadata.get(Constants.TAGS) == null)
            photoRepository.findAll();

        // for now search by tag only supported
        List<Photo> photos = photoRepository.findAllByTag(metadata.get(Constants.TAGS));
        for (Photo photo : photos) {
           photo.getMetaData();
           updateDownloadUrl(photo);
        }

        return photos;
    }

    @Override
    public Photo likePhoto(String uuid, String userId) throws PhotoNotFoundException, InvalidOperaiton {
        Photo photo = photoRepository.findById(uuid).orElse(null);
        if (photo == null)
            throw new PhotoNotFoundException("Can't like a photo that doesn't exist "+ uuid);

        if (photo.getOwner().getUserId() == userId)
            throw new InvalidOperaiton("Owner can't like their own photo "+ userId +"");

        photo.addToLikes(userId);
        return photoRepository.save(photo);
    }

    private User userAlbumManagement(User userE, String albumTitle) {
        // if creating new album for an existing user, update albums for user
        Album album = albumRepository.findByTitle(albumTitle);
        if(!userE.getAlbums().contains(album)) {
            albumRepository.save(new Album(albumTitle, userE));
        }

        return userE;
    }

    private void updateDownloadUrl(Photo photo) {
        photo.setDownloadUrl(host+context+photo.getUuid());
    }

    /*
    private User fetchOrCreateOrUpdateUser(User user) throws UserAccountNotFoundException {
        User userE = userRepository.findById(user.getUserId()).orElse(null);
        // if not existing uesr then create one
        if (userE == null) {
            if(!isAutoCreateUserAccountIfNotExists)
                throw new UserAccountNotFoundException("User account not found with id "+user.getUserId());

            user.addToAlbum(new Album(user.getAlbum()));
            userE = userRepository.save(user);
        }
        // if creating new album for an existing user, update albums for user
        Album album = albumRepository.findByTitle(user.getAlbum());
        if(!userE.getAlbums().contains(album)) {
            albumRepository.save(new Album(user.getAlbum(), userE));
        }

        return userE;
    }
    */


}
