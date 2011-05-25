/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.biggrid.plier.tools;

import java.util.ArrayList;
import java.util.List;
import nl.biggrid.plier.opmv11.OPMGraph;
import org.hibernate.HibernateException;
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
        sessionFactory = new Configuration().configure(propFile).buildSessionFactory();
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
            if (tx!=null) tx.rollback();
            he.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    public OPMGraph readOPMGraph(final String gid) {
        return null;
    }

    public Object readObject(final Class type, final String objId) {
        if (type.equals(OPMGraph.class)) {
            return readOPMGraph(objId);
        }
        return null;
    }

    public List<OPMGraph> readAll() {
        //Query query = pm.newQuery("javax.jdo.query.SQL", "SELECT GRAPH_ID FROM OPM_GRAPH");
        List<OPMGraph> experiments = new ArrayList<OPMGraph>();
        List<Object> results = null;//(List<Object>) query.execute();
        for (Object o : results) {
            OPMGraph opm = new OPMGraph();
            opm.setId((String) o);
            experiments.add(opm);
        }
        return experiments;
    }
}
