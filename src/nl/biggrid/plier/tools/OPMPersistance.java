/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.biggrid.plier.tools;

import java.util.Collection;
import java.util.Collections;

import java.util.List;
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

    static String toString(OPMGraph aGraph) {
        String strGraph = "Graph: "+aGraph.getId();
        strGraph += "\nAccounts: ";
        for (Account acc : aGraph.getAccounts().getAccount()) {
            strGraph += (acc.getId()+", ");
        }
        strGraph += ("\nAgents: "+aGraph.getAgents().getAgent().size());
        for (Agent agt : aGraph.getAgents().getAgent()) {
            strGraph += ("\n\t"+agt.getId()+":\n\t\taccounts: ");
            for (Account acc : agt.getAccount()) {
                strGraph += (acc.getId()+", ");
            }
        }
        strGraph += ("\nArtifacts: "+aGraph.getArtifacts().getArtifact().size());
        strGraph += ("\nProcesses: "+aGraph.getProcesses().getProcess().size());
        for (nl.biggrid.plier.opm.Process proc : aGraph.getProcesses().getProcess()) {
            strGraph += ("\n\t"+proc.getId()+":\n\t\tannotations: ");
            for (EmbeddedAnnotation ann : proc.getAnnotation()) {
                strGraph += (ann.getId()+", ");
            }
        }
        strGraph += ("\nDependencies: "+aGraph.getCausalDependencies().getDependency().size());
        strGraph += ("\nAnnotations: "+aGraph.getAnnotations().getAnnotation().size());
        return strGraph;
    }

    public static void main(String args[]) {
        PersistenceManager persistenceManager = PersistenceManager.instance();
        persistenceManager.init("hibernate.cfg.xml");
        //persistenceManager.persist( createOPM() );
        OPMGraph graph = (OPMGraph) persistenceManager.get(OPMGraph.class, new Long(2));
        System.out.println("### Got graph '"+graph.getId()+"'");
        String xmlGraph = PersistenceManager.toXML(graph);
        //System.out.println("### CONVERTING TO XML:\n"+xmlGraph);
        OPMGraph graph2 = PersistenceManager.fromXML(xmlGraph);
        System.out.println("\n### BACK TO OBJECT:\n"+toString(graph2));
        //System.out.println("\n### BACK TO OBJECT:\n"+graph2.getId());
        //graph.setId("Updated Victoria Sponge Cake Provenance");
        //persistenceManager.update(graph);
        //persistenceManager.delete(graph);
        //List<OPMGraph> allData = persistenceManager.getAll();
        //System.out.println("### Got "+allData.size()+" graphs ...");
    }
}
