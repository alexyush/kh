package com.epam.edu.kh.business.dao.record;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.epam.edu.kh.business.entity.Record;

public class RecordDaoImpl implements RecordDao {

    public RecordDaoImpl() {

    }

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public final void saveRecord(final Record record) {
        sessionFactory.getCurrentSession().save(record);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public final List<Record> getListRecords() {
        return sessionFactory.getCurrentSession().createQuery("from Record")
                .setMaxResults(15).list();
    }

    @Transactional
    public final Record getRecord(final long id) {
        return (Record) sessionFactory.getCurrentSession()
                .get(Record.class, id);
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
}
