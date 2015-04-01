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

<<<<<<< HEAD:src/main/java/com/epam/edu/kh/business/entity/Tag.java
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
=======
    @RelatedTo(type = "tagged",direction = Direction.BOTH) @JsonIgnore
>>>>>>> master:src/main/java/com/epam/edu/kh/business/domain/Tag.java
    private Set<Record> records = new HashSet<Record>();

    public Tag() {
    }

    public Tag(String value) {
<<<<<<< HEAD:src/main/java/com/epam/edu/kh/business/entity/Tag.java
        this.id = 1;
=======
>>>>>>> master:src/main/java/com/epam/edu/kh/business/domain/Tag.java
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
<<<<<<< HEAD:src/main/java/com/epam/edu/kh/business/entity/Tag.java

=======
>>>>>>> master:src/main/java/com/epam/edu/kh/business/domain/Tag.java
}
