package com.epam.edu.kh.business.dao.record;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epam.edu.kh.business.dao.tag.TagDao;
import com.epam.edu.kh.business.entity.Record;

@Component("recordDaoImpl")
public class RecordDaoImpl implements RecordDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private TagDao tagDao;

    @Transactional
    public final void save(final Record record) {
        sessionFactory.getCurrentSession().save(record);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public final List<Record> getTop(final int count) {
        return sessionFactory.getCurrentSession().createQuery("select rec from Record as rec order by rec.id desc").setMaxResults(count).list();
    }

    @Transactional
    public final void delete(final Long id) {
        sessionFactory.getCurrentSession().createQuery("delete from Record u where u.id=:id").setParameter("id", id).executeUpdate();
    }

    @Transactional
    public final void update(final Record record) {
        sessionFactory.getCurrentSession().merge(record);
    }

    @Transactional
    public final Long getDateOfLastInsertedRecord(String source) {

        Long date = (Long) sessionFactory.getCurrentSession().createQuery("select max(rec.dateOfCreate) from Record rec where rec.source=:source")
                .setParameter("source", source).uniqueResult();
        return date;
    }

    @Transactional
    public final Record get(final Long id) {
        return (Record) sessionFactory.getCurrentSession().get(Record.class, id);
    }

    @Transactional
    public final void insert(final Record record) {
        record.getTags().addAll(tagDao.getFromMessage(record.getMessage()));
        save(record);
    }
}
