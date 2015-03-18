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

    public Tag() {
    }

    public Tag(int id, String value) {
        this.id = id;
        this.name = value;
    }

    public final Set<Record> getRecords() {
        return this.records;
    }

    public final void setRecords(Set<Record> records) {
        this.records = records;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getName() {
        return this.name;
    }

    public final long getId() {
        return id;
    }

    public final void setId(long id) {
        this.id = id;
    }

}
