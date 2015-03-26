package com.epam.edu.kh.business.dao.tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;

@Component("tagDaoImpl")
public class TagDaoImpl implements TagDao {

    @Autowired
    private SessionFactory sessionFactory;

    public final void save(final Tag tag) {
        sessionFactory.getCurrentSession().save(tag);
    }

    @Transactional(readOnly = true)
    public final Tag get(final long id) {
        return (Tag) sessionFactory.getCurrentSession().get(Tag.class, id);
    }

    public final Tag getByName(final String tagName) {

        Tag tag = (Tag) sessionFactory.getCurrentSession().createQuery("from Tag u where u.name=:name").setParameter("name", tagName).uniqueResult();
        return tag;
    }

    @Transactional
    public final Set<Tag> getFromMessage(final String recordMessage) {

        String[] hashTags = recordMessage.split(" ");
        Set<String> uniqueHashTags = new HashSet<String>();

        for (String tag : hashTags) {
            if (tag.startsWith("#")) {
                uniqueHashTags.add(tag.substring(1));
            }
        }
        Set<Tag> tags = new HashSet<Tag>();
        for (String tag : uniqueHashTags) {
            tags.add(insert(tag));
        }
        return tags;
    }

    public final Tag insert(final String tagName) {

        if (getByName(tagName) != null) {
            return getByName(tagName);
        } else {
            Tag tag = new Tag(tagName);
            save(tag);
            return tag;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Tag> getTopTags(Integer topTags) {
        return sessionFactory.getCurrentSession()
                .createQuery("select tag from Tag as tag join tag.records as tr group by tag.id order by count(tag.id) desc").setMaxResults(topTags)
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<Record> getRecordsByTagNames(List<String> tagNames) {
        return sessionFactory.getCurrentSession().createQuery("select rec from Record as rec join rec.tags as tg where tg.name IN :name")
                .setParameterList("name", tagNames).list();
    }
}
