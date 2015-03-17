package com.epam.edu.kh.business.dao.record;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epam.edu.kh.business.entity.Record;

@Component("recordDaoImpl")
public class RecordDaoImpl implements RecordDao {

    public RecordDaoImpl() {

    }

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public final void save(final Record record) {
        sessionFactory.getCurrentSession().save(record);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public final List<Record> getTop(int count) {
        return sessionFactory.getCurrentSession().createQuery("from Record")
                .setMaxResults(count).list();
    }

    @Transactional
    public final void delete(Long id) {
        sessionFactory.getCurrentSession()
                .createQuery("delete from Record u where u.id=:id")
                .setParameter("id", id).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public final List<Record> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Record")
                .list();
    }

    @Transactional
    public final void update(final Record rec) {
        sessionFactory.getCurrentSession().merge(rec);
    }

    @Transactional
    public final Long getDateOfLastInsertedRecord() {

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
    public final Record get(Long id) {
        return (Record) sessionFactory.getCurrentSession()
                .get(Record.class, id);
    }
}
