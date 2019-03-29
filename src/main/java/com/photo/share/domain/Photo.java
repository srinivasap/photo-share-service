package com.photo.share.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
 * Data model for photo entity type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "PHOTO")
public class Photo {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "PHOTO_ID")
    private String uuid;

    @Column(name = "TITLE")
    private String title;

    @JsonIgnore
    @Lob
    @Column(name = "DATA")
    private byte[] data;

    @JsonIgnore
    @Column(name = "UPDATED_DATE")
    private Date uploadedDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MetaData metaData;

    @Column(name = "ALBUMS")
    private String albums;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User owner;

    @JoinColumn(name = "LIKES")
    private String likes;

    @Transient
    private String downloadUrl;

    public Photo() {}

    public Photo(String uuid) {
        this.uuid = uuid;
    }

    public Photo(String title, byte[] data) {
        this.title = title;
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }

    public void addToAlbum(String ablum) {
        if (albums == null || albums.isEmpty()) {
            this.albums = ablum;
            return;
        }
        albums = albums + "," + ablum;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public void addToLikes(String userId) {
       if (this.likes == null) {
           this.likes = userId;
           return;
       }
       this.likes += ","+userId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo)) return false;
        Photo photo = (Photo) o;
        return Objects.equals(getUuid(), photo.getUuid()) &&
                Objects.equals(getOwner(), photo.getOwner());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUuid(), getOwner());
    }
}
