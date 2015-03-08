package com.epam.edu.kh.business.dao.tag;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
 
import com.epam.edu.kh.business.entity.Tag;

public class TagDaoImpl implements TagDao {

    public TagDaoImpl() {

    }

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public final void saveTag(final Tag tag) {
        sessionFactory.getCurrentSession().save(tag);
    }

    @Transactional
    public final Tag getTag(final long id) {
        return (Tag) sessionFactory.getCurrentSession().get(Tag.class, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public final List<Tag> getAllTags() {
        return sessionFactory.getCurrentSession().createQuery("from Tag")
                .list();
    }
    @Transactional
    public Tag getTagByName(String name) {

        return (Tag) sessionFactory.getCurrentSession()
                .createQuery("from Tag u where u.name=:name")
                .setParameter("name", name).uniqueResult();
    }

}
