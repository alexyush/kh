package com.epam.edu.kh.business.neo4j.entity;

import javax.persistence.Column;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Record {

    @GraphId
    @Indexed(unique=true) 
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecordPhotoUrl() {
        return recordPhotoUrl;
    }

    public void setRecordPhotoUrl(String recordPhotoUrl) {
        this.recordPhotoUrl = recordPhotoUrl;
    }

    private String userName;

    private String sourceUrl;

    private String userProfileUrl;

    private String userPhotoUrl;

    private String message;

    private String recordPhotoUrl;
    
    public Record(){
        
    }

}
