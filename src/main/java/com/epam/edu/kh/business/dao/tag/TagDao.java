package com.epam.edu.kh.business.dao.tag;

import java.util.List;
 
import com.epam.edu.kh.business.entity.Tag;

public interface TagDao {
    void saveTag(Tag tag);

    Tag getTag(long id);

    List<Tag> getAllTags();

    Tag getTagByName(String name);
}
