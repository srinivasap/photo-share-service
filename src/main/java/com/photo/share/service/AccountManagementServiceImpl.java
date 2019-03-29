package com.photo.share.service;

import com.photo.share.domain.User;
import com.photo.share.exception.InvalidOperaiton;
import com.photo.share.exception.UserAccountAlreadyExistException;
import com.photo.share.exception.UserAccountNotFoundException;
import com.photo.share.persistence.UserRepository;
import com.photo.share.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "user")
public class AccountManagementServiceImpl implements AccountManagementService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountManagementServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) throws UserAccountAlreadyExistException {
        // TODO - validation
        User userE = userRepository.findById(user.getUserId()).orElse(null);
        if (userE != null) {
            throw new UserAccountAlreadyExistException(String.format(Constants.ERROR_MESSAGE_USER_EXISTS, user.getUserId()));
        }
        // TODO - do more validations before adding account like email etc
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String userId, User user) throws UserAccountNotFoundException, InvalidOperaiton {
        // TODO - validation
        User userE = userRepository.findById(userId).orElse(null);
        // is the user account exists to do update operation
        if (userE == null) {
            throw new UserAccountNotFoundException(String.format(Constants.ERROR_MESSAGE_USER_NOT_FOUND, user.getUserId()));
        }
        // can't update someone else user account
        if (!userId.equals(user.getUserId())) {
            throw new InvalidOperaiton(Constants.ERROR_MESSAGE_USER_UPDATE_ID_MISMATCH);
        }

        // TODO - do more validations before updating account
        userE.update(user);
        return userE;
    }

    @Override
    public void removeUser(String userId) throws UserAccountNotFoundException {
        // TODO - validation
        User userE = userRepository.findById(userId).orElse(null);
        if (userE == null) {
            throw new UserAccountNotFoundException(String.format(Constants.ERROR_MESSAGE_USER_NOT_FOUND, userId));
        }
        userRepository.delete(userE);
    }

    @Override
    public User retrieveUser(String userId) throws UserAccountNotFoundException {
        // TODO - validation
        User userE = userRepository.findById(userId).orElse(null);
        if (userE == null) {
            throw new UserAccountNotFoundException(String.format(Constants.ERROR_MESSAGE_USER_NOT_FOUND, userId));
        }

        return userE;
    }
}
