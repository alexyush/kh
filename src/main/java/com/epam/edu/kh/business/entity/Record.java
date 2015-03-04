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

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "userName")
	private String userName;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "sourceUrl")
	private String sourceUrl;

	public String getSourceUrl() {
		return this.sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	@Column(name = "userProfileUrl")
	private String userProfileUrl;

	public String getUserProfileUrl() {
		return this.userProfileUrl;
	}

	public void setUserProfileUrl(String userProfileUrl) {
		this.userProfileUrl = userProfileUrl;
	}

	@Column(name = "userPhotoUrl")
	private String userPhotoUrl;

	public String getUserPhotoUrl() {
		return this.userPhotoUrl;
	}

	public void setUserPhotoUrl(String userPhotoUrl) {
		this.userPhotoUrl = userPhotoUrl;
	}

	@Column(name = "message")
	private String message;

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "recordPhotoUrl")
	private String recordPhotoUrl;

	public String getRecordPhotoUrl() {
		return this.recordPhotoUrl;
	}

	public void setRecordPhotoUrl(String recordPhotoUrl) {
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
	public int hashCode() {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;

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
