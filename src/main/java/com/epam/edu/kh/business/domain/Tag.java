package com.epam.edu.kh.business.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo; 

import com.fasterxml.jackson.annotation.JsonIgnore;

@NodeEntity
public class Tag implements Serializable {
    private static final long serialVersionUID = -1694209144530835559L;

    @GraphId
    private Long id;
    
    @Indexed(unique = true)
    private String name;

    @RelatedTo(type = "tagged",direction = Direction.BOTH) @JsonIgnore
    private Set<Record> records = new HashSet<Record>();

    public Tag() {
    }

    public Tag(String value) {
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
