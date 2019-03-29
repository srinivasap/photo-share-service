package com.photo.share.service;

import com.photo.share.domain.User;
import com.photo.share.exception.InvalidOperaiton;
import com.photo.share.exception.UserAccountAlreadyExistException;
import com.photo.share.exception.UserAccountNotFoundException;

public interface AccountManagementService {

    User createUser(User user) throws UserAccountAlreadyExistException;

    User updateUser(String userId, User user) throws UserAccountNotFoundException, InvalidOperaiton;

    void removeUser(String userId) throws UserAccountNotFoundException;

    User retrieveUser(String userId) throws UserAccountNotFoundException;
}
