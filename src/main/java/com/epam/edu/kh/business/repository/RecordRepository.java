package com.epam.edu.kh.business.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.epam.edu.kh.business.domain.Record;

public interface RecordRepository extends GraphRepository<Record> {

    @Query("match (rec:Record) return rec order by rec.dateOfCreate desc limit {0}")
    Iterable<Record> getTopRecords(Integer topRecords);

    @Query("match (rec:Record) where rec.source={0} return max(rec.dateOfCreate)")
    Long getDateOfLastInsertedRecord(String source);

}
