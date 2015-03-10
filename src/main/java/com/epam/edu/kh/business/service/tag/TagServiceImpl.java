package com.epam.edu.kh.business.service.tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.epam.edu.kh.business.dao.tag.TagDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;

@Component("tagServiceImpl")
public class TagServiceImpl implements TagService {

    @Autowired
    @Qualifier("tagDaoImpl")
    private TagDao tagDao;

    public final Set<Tag> getTagsFromMessage(String message) {

        String[] hashTags = message.split(" ");
        Set<String> uniqueHashTags = new HashSet<String>();

        for (String tag : hashTags) {
            if (tag.startsWith("#")) {
                uniqueHashTags.add(tag.substring(1));
            }
        }
        Set<Tag> tags = new HashSet<Tag>();
        for (String tag : uniqueHashTags) {
            try {
                tags.add(insertTag(tag));
            } catch (ConstraintViolationException ex) {

                tags.add(tagDao.getTagByName(tag));
            }
        }
        return tags;
    }

    public final Tag insertTag(String tagName)
            throws ConstraintViolationException {

        Tag tag = new Tag(1, tagName);
        tagDao.saveTag(tag);
        return tag;
    }

    public TagServiceImpl() {

    }

    public final Set<Record> getRecordsByTagName(Set<String> tags) {

        Set<Record> records = new HashSet<Record>();
        for (String tagsName : tags) {
            records.addAll(tagDao.getTagByName(tagsName).getRecords());
        }

        return records;
    }

    public final List<Tag> getTopTags() {
        List<Tag> tags = tagDao.getAllTags();
        if (tags.size() != 0) {
            qSort(tags, 0, tags.size() - 1);
        }
        if (tags.size() <= 20) {
            return tags;
            }
        else {
            return tags.subList(0, 20);
        }

    }

    private void qSort(List<Tag> tags, int start, int end) {

        int i = start;
        int j = end;
        int x = tags.get((i + j) / 2).getRecords().size();
        do {

            while (tags.get(i).getRecords().size() < x) {
                ++i;
            }
            while (tags.get(j).getRecords().size() > x) {
                --j;
            }
            if (i <= j) {
                Tag temp = tags.get(i);
                tags.set(i, tags.get(j));
                tags.set(j, temp);
                i++;
                j--;
            }
        } while (i <= j);

        if (start < j) {
            qSort(tags, start, j);
        }
        if (i < end) {
            qSort(tags, i, end);
        }
    }
}
