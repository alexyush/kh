package com.epam.edu.kh.business.service.tag;

import java.util.List;
import java.util.Set;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.epam.edu.kh.business.service.record.Names;

public interface TagService {

    Set<Record> getRecordsByTagName(Names tagsNames);

    List<Tag> getTopTags();
}
