package com.photo.share.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "META_DATA")
public class MetaData {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "META_DATA_ID")
    private Long id;

    @Column(name = "TAGS")
    private String tags;

    @Column(name = "IMAGE_HEIGHT")
    private String imageHeight;

    @Column(name = "IMAGE_WIDTH")
    private String imageWidth;

    @OneToOne(fetch = FetchType.LAZY)
    private Photo photo;

    public MetaData() {}

    public MetaData(String tags) {
        this();
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }
}
