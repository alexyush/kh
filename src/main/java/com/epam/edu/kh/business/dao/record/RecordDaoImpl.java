package com.epam.edu.kh.business.dao.record;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.epam.edu.kh.business.dao.tag.TagDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;

@Component("recordDaoImpl")
public class RecordDaoImpl implements RecordDao {

    public RecordDaoImpl() {

    }

    @Autowired
    @Qualifier("tagDaoImpl")
    private TagDao tagDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public final void saveRecord(final Record record) {
        sessionFactory.getCurrentSession().save(record);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public final List<Record> getTopRecords(int count) {
        return sessionFactory.getCurrentSession().createQuery("from Record")
                .setMaxResults(count).list();
    }

    @Transactional
    public final void delete(final long id) {
        sessionFactory.getCurrentSession()
                .createQuery("delete from Record u where u.id=:id")
                .setParameter("id", id).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public final List<Record> getAllRecords() {
        return sessionFactory.getCurrentSession().createQuery("from Record")
                .list();
    }

    @Transactional
    public final void updateRecord(final Record rec) {
        sessionFactory.getCurrentSession().merge(rec);
    }

    @Transactional
    public final Long getLastDateOfCreate() {

        Long dateOfCreate;
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Record.class)
                .setProjection(Projections.max("dateOfCreate"));
        dateOfCreate = (Long) criteria.uniqueResult();
        if (dateOfCreate == null) {
            throw new NullPointerException("Vot tut u tebya null");
        } else {
            return dateOfCreate;
        }
    }

    @Transactional
    public final void insertRecord(final Record record)
            throws IllegalArgumentException, IOException {

        record.setTags(addTagsToRecord(record.getTags(),
                tagDao.getTagsFromMessage(record.getMessage())));
        record.setMessage(cutString(160, record.getMessage()));
        saveRecord(record);

    }

    private String cutString(int i, String str) {
        String newString;
        if (i < str.length()) {
            newString = str.substring(0, i);
        } else {
            return str;
        }
        newString = newString.concat("...");
        return newString;
    }

    private Set<Tag> addTagsToRecord(Set<Tag> recordsTags, Set<Tag> tags) {

        for (Tag tag : tags) {
            recordsTags.add(tag);
        }
        return recordsTags;
    }
}
