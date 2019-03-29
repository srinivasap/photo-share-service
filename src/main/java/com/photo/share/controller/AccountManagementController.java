package com.photo.share.controller;

import com.photo.share.domain.Message;
import com.photo.share.domain.User;
import com.photo.share.exception.InvalidOperaiton;
import com.photo.share.exception.UserAccountAlreadyExistException;
import com.photo.share.exception.UserAccountNotFoundException;
import com.photo.share.service.AccountManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountManagementController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountManagementController.class);

    @Autowired
    private AccountManagementService accountManagementService;


    /**
     * Endpoint to create user
     *
     * @return json response
     */
    @RequestMapping(
            value = "/accounts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createAccount(@RequestBody User user) {
        ResponseEntity<?> response = null;

        try {
            User userE = accountManagementService.createUser(user);
            response = new ResponseEntity<>(userE, HttpStatus.OK);
        } catch (UserAccountAlreadyExistException ue) {
            response = new ResponseEntity<>(new Message(Message.Status.ERROR, ue.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Endpoint to  download photos
     *
     * @return photo as stream
     */
    @RequestMapping(
            value = "/accounts/{userid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> retrieveUserAccount(
            @PathVariable(name="userid", required=true) String userId) {
        ResponseEntity<?> response = null;

        try {
            User userE = accountManagementService.retrieveUser(userId);
            response = new ResponseEntity<>(userE, HttpStatus.OK);
        } catch (UserAccountNotFoundException e) {
            response = new ResponseEntity<>(new Message(Message.Status.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return response;
    }


    /**
     * Endpoint to create user
     *
     * @return json response
     */
    @RequestMapping(
            value = "/accounts/{userid}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateAccount(
            @RequestBody User user,
            @PathVariable(name="userid", required=true) String userId) {
        ResponseEntity<?> response = null;

        try {
            User userE = accountManagementService.updateUser(userId, user);
            response = new ResponseEntity<>(userE, HttpStatus.OK);
        } catch (UserAccountNotFoundException ue) {
            response = new ResponseEntity<>(new Message(Message.Status.ERROR, ue.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidOperaiton io) {
            response = new ResponseEntity<>(new Message(Message.Status.ERROR, io.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Endpoint to  delete user account
     *
     * @return photo as stream
     */
    @RequestMapping(
            value = "/accounts/{userid}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> removeUserAccount(
            @PathVariable(name="userid", required=true) String userId) {
        ResponseEntity<?> response = null;

        try {
            accountManagementService.removeUser((userId));
            response = new ResponseEntity<>(new Message(Message.Status.SUCCESS, "Removed"), HttpStatus.OK);
        } catch (UserAccountNotFoundException e) {
            response = new ResponseEntity<>(new Message(Message.Status.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return response;
    }


}
