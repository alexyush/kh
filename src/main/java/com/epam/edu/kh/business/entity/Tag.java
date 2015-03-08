package com.epam.edu.kh.business.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Tag")
public class Tag implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1694209144530835559L;

    @Id
    @GeneratedValue
    @Column(name = "tagId")
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private Set<Record> records = new HashSet<Record>();

    public Set<Record> getRecords() {
        return this.records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tag() {

    }

    public Tag(int id, String value) {
        this.id = id;
        this.name = value;
    }

}
