/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.biggrid.plier.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import java.util.List;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import nl.biggrid.plier.opmv11.Account;
import nl.biggrid.plier.opmv11.AccountRef;
import nl.biggrid.plier.opmv11.Accounts;
import nl.biggrid.plier.opmv11.Agent;
import nl.biggrid.plier.opmv11.AgentRef;
import nl.biggrid.plier.opmv11.Agents;
import nl.biggrid.plier.opmv11.Artifact;
import nl.biggrid.plier.opmv11.ArtifactRef;
import nl.biggrid.plier.opmv11.Artifacts;
import nl.biggrid.plier.opmv11.CausalDependencies;
import nl.biggrid.plier.opmv11.OPMGraph;
import nl.biggrid.plier.opmv11.Process;
import nl.biggrid.plier.opmv11.ProcessRef;
import nl.biggrid.plier.opmv11.Processes;
import nl.biggrid.plier.opmv11.Role;
import nl.biggrid.plier.opmv11.Used;
import nl.biggrid.plier.opmv11.WasControlledBy;
import nl.biggrid.plier.opmv11.WasGeneratedBy;
/**
 *
 * @author vguevara
 */
public class OPMPersistance00 {

    public static OPMGraph createOPM() {
        Account mainAccount = new Account();
        mainAccount.setId("main");
        Accounts accounts = new Accounts();
        accounts.getAccount().add(mainAccount);

        AccountRef accountRef = new AccountRef();
        accountRef.setRef("main");

        Agent agent1 = new Agent();
        agent1.setId("John");
        agent1.getAccount().add(accountRef);
        Agent agent2 = new Agent();
        agent2.setId("bakery");
        agent2.getAccount().add(accountRef);

        Agents agents = new Agents();
        agents.getAgent().add(agent1);
        agents.getAgent().add(agent2);

        Process process = new Process();
        process.setId("baking cake with ingredients");
        process.getAccount().add(accountRef);
        Processes processes = new Processes();
        processes.getProcess().add(process);

        Artifact a1 = new Artifact();
        a1.setId("butter");
        a1.getAccount().add(accountRef);
        Artifact a2 = new Artifact();
        a2.setId("eggs");
        a2.getAccount().add(accountRef);
        Artifact a3 = new Artifact();
        a3.setId("flour");
        a3.getAccount().add(accountRef);
        Artifact a4 = new Artifact();
        a4.setId("sugar");
        a4.getAccount().add(accountRef);
        Artifact a5 = new Artifact();
        a5.setId("cake");
        a5.getAccount().add(accountRef);

        Artifacts artifacts = new Artifacts();
        artifacts.getArtifact().add(a1);
        artifacts.getArtifact().add(a2);
        artifacts.getArtifact().add(a3);
        artifacts.getArtifact().add(a4);
        artifacts.getArtifact().add(a5);

        AgentRef agent1Ref = new AgentRef();
        agent1Ref.setRef(agent1.getId());
        AgentRef agent2Ref = new AgentRef();
        agent2Ref.setRef(agent2.getId());

        ProcessRef processRef = new ProcessRef();
        processRef.setRef(process.getId());

        ArtifactRef a1Ref = new ArtifactRef();
        a1Ref.setRef(a1.getId());
        ArtifactRef a2Ref = new ArtifactRef();
        a2Ref.setRef(a2.getId());
        ArtifactRef a3Ref = new ArtifactRef();
        a3Ref.setRef(a3.getId());
        ArtifactRef a4Ref = new ArtifactRef();
        a4Ref.setRef(a4.getId());
        ArtifactRef a5Ref = new ArtifactRef();
        a5Ref.setRef(a5.getId());
        
        Used u1 = new Used();
        u1.setId("used1");
        u1.setEffect(processRef);
        Role butterRole = new Role();
        butterRole.setId("(butter)");
        butterRole.setValue("(butter)");
        u1.setRole(butterRole);
        u1.setCause(a1Ref);
        u1.getAccount().add(accountRef);

        Used u2 = new Used();
        u2.setId("used2");
        u2.setEffect(processRef);
        Role eggRole = new Role();
        eggRole.setId("(egg)");
        eggRole.setValue("(egg)");
        u2.setRole(eggRole);
        u2.setCause(a2Ref);
        u2.getAccount().add(accountRef);

        Used u3 = new Used();
        u3.setId("used3");
        u3.setEffect(processRef);
        Role flourRole = new Role();
        flourRole.setId("(flour)");
        flourRole.setValue("(flour)");
        u3.setRole(flourRole);
        u3.setCause(a3Ref);
        u3.getAccount().add(accountRef);

        Used u4 = new Used();
        u4.setId("used4");
        u4.setEffect(processRef);
        Role sugarRole = new Role();
        sugarRole.setId("(sugar)");
        sugarRole.setValue("(sugar)");
        u4.setRole(sugarRole);
        u4.setCause(a4Ref);
        u4.getAccount().add(accountRef);

        WasGeneratedBy wgb = new WasGeneratedBy();
        wgb.setId("wgb1");
        wgb.setEffect(a5Ref);
        Role cakeRole = new Role();
        cakeRole.setId("(cake)");
        cakeRole.setValue("(cake)");
        wgb.setRole(cakeRole);
        wgb.setCause(processRef);
        wgb.getAccount().add(accountRef);

        WasControlledBy wcb1 = new WasControlledBy();
        wcb1.setId("wcb1");
        wcb1.setEffect(processRef);
        Role bakerRole = new Role();
        bakerRole.setId("(baker)");
        bakerRole.setValue("(baker)");
        wcb1.setRole(bakerRole);
        wcb1.setCause(agent1Ref);
        wcb1.getAccount().add(accountRef);

        WasControlledBy wcb2 = new WasControlledBy();
        wcb2.setId("wcb2");
        wcb2.setEffect(processRef);
        Role bakeryRole = new Role();
        bakeryRole.setId("(bakery)");
        bakeryRole.setValue("(bakery)");
        wcb2.setRole(bakeryRole);
        wcb2.setCause(agent2Ref);
        wcb2.getAccount().add(accountRef);

        CausalDependencies dependencies = new CausalDependencies();
        dependencies.getDependency().add(u1);
        dependencies.getDependency().add(u2);
        dependencies.getDependency().add(u3);
        dependencies.getDependency().add(u4);
        dependencies.getDependency().add(wgb);
        dependencies.getDependency().add(wcb1);
        dependencies.getDependency().add(wcb2);

        OPMGraph graph = new OPMGraph();
        graph.setId("Victoria_Sponge_Cake_Provenance");
        graph.setAccounts(accounts);
        graph.setAgents(agents);
        graph.setArtifacts(artifacts);
        graph.setProcesses(processes);
        graph.setCausalDependencies(dependencies);
        return graph;
    }


    public static void main(String args[])
    {
        SessionFactory sessionFactory = new Configuration()
            .configure() // configures settings from hibernate.cfg.xml
            .buildSessionFactory();

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        OPMGraph graph = createOPM();
        session.save( graph );
        session.getTransaction().commit();
        session.close();
    }

}
