package com.epam.edu.kh.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Record")
public class Record {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "sourceUrl")
    private String sourceUrl;

    @Column(name = "userProfileUrl")
    private String userProfileUrl;

    @Column(name = "userPhotoUrl")
    private String userPhotoUrl;

    @Column(name = "message")
    private String message;

    @Column(name = "recordPhotoUrl")
    private String recordPhotoUrl;

    public final long getId() {
        return this.id;
    }

    public final void setId(long id) {
        this.id = id;
    }

    public final String getUserName() {
        return this.userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public final String getSourceUrl() {
        return this.sourceUrl;
    }

    public final void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public final String getUserProfileUrl() {
        return this.userProfileUrl;
    }

    public final void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public final String getUserPhotoUrl() {
        return this.userPhotoUrl;
    }

    public final void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public final String getMessage() {
        return this.message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final String getRecordPhotoUrl() {
        return this.recordPhotoUrl;
    }

    public final void setRecordPhotoUrl(String recordPhotoUrl) {
        this.recordPhotoUrl = recordPhotoUrl;
    }

    public Record(long id, String userName, String sourceUrl,
            String userProfileUrl, String userPhotoUrl, String message,
            String recordPhotoUrl) {

        this.id = id;
        this.userName = userName;
        this.sourceUrl = sourceUrl;
        this.userProfileUrl = userProfileUrl;
        this.userPhotoUrl = userPhotoUrl;
        this.message = message;
        this.recordPhotoUrl = recordPhotoUrl;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result
                + ((recordPhotoUrl == null) ? 0 : recordPhotoUrl.hashCode());
        result = prime * result
                + ((sourceUrl == null) ? 0 : sourceUrl.hashCode());
        result = prime * result
                + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result
                + ((userPhotoUrl == null) ? 0 : userPhotoUrl.hashCode());
        result = prime * result
                + ((userProfileUrl == null) ? 0 : userProfileUrl.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Record other = (Record) obj;

        if (id == 0) {
            if (other.id != 0)
                return false;
        } else if (id != other.id)
            return false;

        if (sourceUrl == null) {
            if (other.sourceUrl != null)
                return false;
        } else if (!sourceUrl.equals(other.sourceUrl))
            return false;

        return true;
    }

    public Record() {
    }

}
