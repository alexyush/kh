package com.epam.edu.kh.business.dao.tag;

import java.util.List;
import java.util.Set;
import com.epam.edu.kh.business.entity.Tag;

public interface TagDao {
    void save(Tag tag);

    Tag get(long id);

    Tag insert(String tagName);

    Tag getByName(String name);

    List<Tag> getAll();

    Set<Tag> getFromMessage(String message);

}
