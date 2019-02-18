package com.photo.share.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @JsonIgnore
    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @JsonIgnore
    @Column(name = "ADDRESS")
    private String address;

    @JsonIgnore
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Album> albums;

    @Transient
    private String album;

    public User() {}

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String album) {
        this();
        this.userId = userId;
        this.album = album;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public void addToAlbum(Album album) {
        if (this.albums == null) this.albums = new HashSet<>();
        this.albums.add(album);
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}
