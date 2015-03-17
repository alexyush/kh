package com.epam.edu.kh.business.dao.tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.epam.edu.kh.business.entity.Tag;

@Component("tagDaoImpl")
public class TagDaoImpl implements TagDao {

    public TagDaoImpl() {

    }

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public final void save(final Tag tag) {
        sessionFactory.getCurrentSession().save(tag);
    }

    @Transactional
    public final Tag get(final long id) {
        return (Tag) sessionFactory.getCurrentSession().get(Tag.class, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public final List<Tag> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Tag")
                .list();
    }

    @Transactional
    public final Tag getByName(String name) {

        Tag tag = (Tag) sessionFactory.getCurrentSession()
                .createQuery("from Tag u where u.name=:name")
                .setParameter("name", name).uniqueResult();

        if (tag == null) {
            throw new NullPointerException("this tag is null");
        } else {
            return tag;
        }
    }
    @Transactional
    public final Set<Tag> getFromMessage(String message) {

        String[] hashTags = message.split(" ");
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
    @Transactional
    public final Tag insert(String tagName) {

        Tag tag = new Tag(1, tagName);
        try {
            Tag tagForCompare = getByName(tagName);
            if (tagName.equals(tagForCompare.getName())) {
                return tagForCompare;
            } else {
                save(tag);
                return tag;
            }
        } catch (NullPointerException ex) {
            save(tag);
            return tag;
        }
    }
}
