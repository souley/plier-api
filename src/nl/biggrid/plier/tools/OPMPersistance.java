/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.biggrid.plier.tools;

import java.util.Collection;
import java.util.Collections;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;

import nl.biggrid.plier.opm.*;
/**
 *
 * @author vguevara
 */
public class OPMPersistance {

    public static OPMGraph createOPM() {
        OPMFactory oFactory = new OPMFactory();
        Account mainAccount = oFactory.newAccount("main");
        Collection<Account> accountList = Collections.singleton(mainAccount);

        Agent agent1 = oFactory.newAgent("John", accountList, "John Doe");
        Agent agent2 = oFactory.newAgent("bakery", accountList, "Fresh Bakery Amsterdam");

        nl.biggrid.plier.opm.Process process = oFactory.newProcess("baking cake with ingredients",
                accountList,
                "bake");

        Artifact a1 = oFactory.newArtifact("butter",
                accountList,
                "100 g butter");
        Artifact a2 = oFactory.newArtifact("eggs",
                accountList,
                "two eggs");

        Artifact a3 = oFactory.newArtifact("flour",
                accountList,
                "100 g flour");
        Artifact a4 = oFactory.newArtifact("sugar",
                accountList,
                "100 g sugar");
        Artifact a5 = oFactory.newArtifact("cake",
                accountList,
                "cake");

//        Agent agent1 = oFactory.newAgent("John", accountList);
//        Agent agent2 = oFactory.newAgent("bakery", accountList);
//
//        nl.biggrid.plier.opm.Process process = oFactory.newProcess("baking cake with ingredients", accountList);
//
//        Artifact a1 = oFactory.newArtifact("butter", accountList);
//        Artifact a2 = oFactory.newArtifact("eggs", accountList);
//        Artifact a3 = oFactory.newArtifact("flour",accountList);
//        Artifact a4 = oFactory.newArtifact("sugar",accountList);
//        Artifact a5 = oFactory.newArtifact("cake", accountList);

        Used u1 = oFactory.newUsed("used1", process, "(butter)", a1, accountList);
        Used u2 = oFactory.newUsed("used2", process, "(egg)", a2, accountList);
        Used u3 = oFactory.newUsed("used3", process, "(flour)", a3, accountList);
        Used u4 = oFactory.newUsed("used4", process, "(sugar)", a4, accountList);

        WasGeneratedBy wgb1 = oFactory.newWasGeneratedBy("wgb1", a5, "cake", process, accountList);

        WasControlledBy wcb1 = oFactory.newWasControlledBy("wcb1", process, "baker", agent1, accountList);
        WasControlledBy wcb2 = oFactory.newWasControlledBy("wcb2", process, "bakery", agent2, accountList);

        OPMGraph graph = oFactory.newOPMGraph("Victoria Sponge Cake Provenance",
                accountList,
                new Overlaps[]{},
                new nl.biggrid.plier.opm.Process[]{process},
                new Artifact[]{a1, a2, a3, a4, a5},
                new Agent[]{agent1, agent2},
                new CausalDependency[]{u1, u2, u3, u4,
                    wgb1,
                    wcb1, wcb2},
                new Annotation[]{});

        return graph;
    }

    static Agent createAgent() {
        OPMFactory oFactory = new OPMFactory();
        Account mainAccount = oFactory.newAccount("main");
        Collection<Account> accountList = Collections.singleton(mainAccount);
        return oFactory.newAgent("John", accountList, "John Doe");
    }
    static void persist(SessionFactory factory, OPMGraph graph) {
        System.out.println("### MAIN::PERSIST ...");
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save( graph );
            tx.commit();
            System.out.println("### GRAPH "+graph.getId()+" -> SAVED ...");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    static void persist(SessionFactory factory, Agent agt) {
        System.out.println("### MAIN::PERSIST ...");
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save( agt );
            tx.commit();
            System.out.println("### AGENT "+agt.getId()+" -> SAVED ...");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    static OPMGraph load(SessionFactory factory) {
        OPMGraph graph = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
//            final String QUERY = "from OPMGraph where id=?";
//            Query hqlQuery = session.createQuery(QUERY);
//            hqlQuery.setString(0, "Victoria Sponge Cake Provenance");
//            //graph = (OPMGraph)hqlQuery.uniqueResult();
//            List<OPMGraph> results = hqlQuery.list();
//            graph = results.get(0);
            //graph = (OPMGraph)session.get(OPMGraph.class, "Victoria Sponge Cake Provenance");
            graph = (OPMGraph)session.get(OPMGraph.class, new Long(2));
            System.out.println("### GRAPH "+graph.getId()+" -> EXTRACTED ...");
            System.out.println("### ACCOUNTS -> "+graph.getAccounts().getAccount().size());
            System.out.println("### OVERLAPS -> "+graph.getAccounts().getOverlaps().size());
            System.out.println("### AGENTS -> "+graph.getAgents().getAgent().size());
            System.out.println("### ARTIFACTS -> "+graph.getArtifacts().getArtifact().size());
            System.out.println("### PROCESSES -> "+graph.getProcesses().getProcess().size());
            System.out.println("### DEPENDENCIES -> "+graph.getCausalDependencies().getDependency().size());
            System.out.println("### ANNOTATIONS -> "+graph.getAnnotation().size());
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return graph;
    }

    public static void main(String args[]) {
        SessionFactory factory = new Configuration().configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        persist(factory, createOPM());
        //persist(factory, createAgent());
        //load(factory);
    }
}
