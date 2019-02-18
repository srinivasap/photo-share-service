package com.photo.share.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "ALBUM")
public class Album {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ALBUM_ID")
    private Long albumId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User owner;

    @Transient
    private List<Photo> photos;

    public Album() {}

    public Album(String title) {
        this();
        this.title = title;
    }

    public Album(String title, User owner) {
        this();
        this.title = title;
        this.owner = owner;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return Objects.equals(getTitle(), album.getTitle()) &&
                Objects.equals(getOwner(), album.getOwner());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTitle(), getOwner());
    }
}
