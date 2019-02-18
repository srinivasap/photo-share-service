package com.photo.share.controller;

import com.photo.share.domain.Message;
import com.photo.share.domain.Photo;
import com.photo.share.exception.InvalidOperaiton;
import com.photo.share.exception.PhotoNotFoundException;
import com.photo.share.exception.UserAccountNotFoundException;
import com.photo.share.service.PhotoShareService;
import com.photo.share.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PhotoShareController {

    private static final Logger LOG = LoggerFactory.getLogger(PhotoShareController.class);

    @Autowired
    private PhotoShareService photoShareService;

    @RequestMapping(
            value = "/photos/{uuid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<?> downloadPhoto(
            @PathVariable(name="uuid", required=true) String uuid) {
        ResponseEntity<?> response = null;
        try {
            Photo photo = photoShareService.getPhotoWithUUID(uuid);
            response = ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getTitle() + "\"")
                        .body(new ByteArrayResource(photo.getData()));
        } catch (PhotoNotFoundException e) {
            response = new ResponseEntity<>(new Message(Message.Status.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @RequestMapping(
            value = "/photos/{userid}/{album}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadPhotos(
            @PathVariable(name="userid", required=true) String userid,
            @PathVariable(name="album", required=false) String album,
            @RequestParam("images") MultipartFile[] images,
            @RequestHeader("tags") String tags) {
        ResponseEntity<?> response = null;

        try {
            Map<String, String> customTags = new HashMap<>();
            customTags.put(Constants.TAGS, tags);

            List<Photo> photosToSave = new ArrayList<>();
            for (MultipartFile image : images) photosToSave.add(new Photo(image.getOriginalFilename(), image.getBytes()));
            List<Photo> photosSaved = photoShareService.savePhotos(userid, album, photosToSave, customTags);

            response = new ResponseEntity<>(photosSaved, HttpStatus.OK);
        } catch (UserAccountNotFoundException ue) {
            response = new ResponseEntity<>(new Message(Message.Status.ERROR, ue.getMessage()), HttpStatus.NOT_FOUND);
        } catch (IOException ioe) {
            LOG.error("Error reading image attachments", ioe);
            Message errorMessage = new Message(Message.Status.ERROR, Constants.ERROR_MESSAGE_RETRY);
            response = new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @RequestMapping(
            value = "/photos/{userid}/{uuid}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removePhoto(
            @PathVariable(name="userid", required=true) String userid,
            @PathVariable(name="uuid", required=true) String uuid) {
        ResponseEntity<?> response = null;

        try {
            photoShareService.deletePhotoWithUUID(userid, uuid);
            Message successMessage = new Message(Message.Status.ERROR, String.format(Constants.SUCCESS_MESSAGE_DELETE, uuid, userid));
            response = new ResponseEntity<>(successMessage, HttpStatus.OK);
        } catch (UserAccountNotFoundException ue) {
            response = new ResponseEntity<>(new Message(Message.Status.ERROR, ue.getMessage()), HttpStatus.NOT_FOUND);
        } catch (PhotoNotFoundException pe) {
            response = new ResponseEntity<>(new Message(Message.Status.ERROR, pe.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidOperaiton io) {
            response = new ResponseEntity<>(new Message(Message.Status.ERROR, io.getMessage()), HttpStatus.FORBIDDEN);
        }

        return response;
    }

    @RequestMapping(
            value = "/photos/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchPhotos(
            @RequestParam("tags") String tags) {
        Map<String, String> searchCriteria = new HashMap<>();
        searchCriteria.put(Constants.TAGS, tags);

        List<Photo> result = photoShareService.searchPhotosWithMetadata(searchCriteria);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
