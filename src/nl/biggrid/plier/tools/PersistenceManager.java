/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.biggrid.plier.tools;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nl.biggrid.plier.opm.OPMGraph;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Souley
 */
public class PersistenceManager {

    static private PersistenceManager instance = null;
    private SessionFactory sessionFactory = null;

    static public PersistenceManager instance() {
        if (null == instance) {
            instance = new PersistenceManager();
        }
        return instance;
    }

    public void init(String propFile) {
        sessionFactory = new Configuration().configure(new File(propFile)).buildSessionFactory();
    }

    public void shutdown() {
    }

    public void persist(final Object o) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            he.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Object get(final Class type, final Serializable oId) {
        Object res = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            res = session.get(type, oId);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            he.printStackTrace();
        } finally {
            session.close();
        }
        return res;
    }

    public void delete(final Object o) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            he.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(final Object o) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            he.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<OPMGraph> getAll() {
        List<OPMGraph> experiments = new ArrayList<OPMGraph>();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from OPMGraph");
            List<Object> results = query.list();
            for (Object o : results) {
                experiments.add((OPMGraph)o);
            }
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            he.printStackTrace();
        } finally {
            session.close();
        }
        return experiments;
    }
}
