package com.epam.edu.kh.business.repository;

import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.epam.edu.kh.business.domain.Record;
import com.epam.edu.kh.business.domain.Tag;

public interface TagRepository extends GraphRepository<Tag> {
    
    @Query("match (tag:Tag) where tag.name={0} return tag")
    Tag findByTagName(String name);
    
    @Query("match (tag:Tag)-[r:tagged]-(rec:Record) with tag,count(r) as tagCount order by tagCount desc limit {0} return tag")
    Iterable<Tag> getTopTags(Integer topTags);
    
    @Query("match (tag:Tag)-[:tagged]-(rec:Record) where tag.name IN {0} return distinct rec order by rec.dateOfCreate desc")
    Iterable<Record> getRecordsByTagNames(List<String> tagNames);
}