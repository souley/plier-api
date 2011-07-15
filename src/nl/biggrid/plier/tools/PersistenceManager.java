/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.biggrid.plier.tools;

import java.io.File;
import java.io.Serializable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import nu.xom.*;
import nl.biggrid.plier.opm.*;
/**
 *
 * @author Souley
 */
public class PersistenceManager {

    static final String OPM_NS = "http://openprovenance.org/model/v1.1.a";
    static final String CHAR_ENCODING = "UTF-8";

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

    public void init(String propFile, Properties properties) {
        sessionFactory = new Configuration().configure(propFile)
                .addProperties(properties)
                .addResource("opm.hbm.xml")
                .buildSessionFactory();
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

    public List executeQuery(String query) {
        List results = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            results = session.createQuery(query).list();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            he.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public List executeSQL(String query) {
        List results = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            results = session.createSQLQuery(query).list();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            he.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public List<Object> executeSQL(String query, Class type, String gid) {
        List<Object> results = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            results = session.createSQLQuery(query).addEntity(type).setString(0, gid).list();
            tx.commit();
        } finally {
            session.close();
        }
        return results;
    }

    static public String toXML(final OPMGraph graph) {
        Element root = (Element) new Element("opm:OPMGraph", OPM_NS);
        root.addAttribute(new Attribute("id", graph.getId()));
        Element accounts = (Element) new Element("opm:Accounts", OPM_NS);
        root.appendChild(accounts);
        for (Account acc : graph.getAccounts().getAccount()) {
            addAccount(accounts, acc);
        }
        Element agents = (Element) new Element("opm:Agents", OPM_NS);
        root.appendChild(agents);
        for (Agent agt : graph.getAgents().getAgent()) {
            addAgent(agents, agt);
        }
        Element artifacts = (Element) new Element("opm:Artifacts", OPM_NS);
        root.appendChild(artifacts);
        for (Artifact art : graph.getArtifacts().getArtifact()) {
            addArtifact(artifacts, art);
        }
        Element processes = (Element) new Element("opm:Processes", OPM_NS);
        root.appendChild(processes);
        for (nl.biggrid.plier.opm.Process process : graph.getProcesses().getProcess()) {
            addProcess(processes, process);
        }
        Element dependencies = (Element) new Element("opm:CausalDependencies", OPM_NS);
        root.appendChild(dependencies);
        for (CausalDependency dep : graph.getCausalDependencies().getDependency()) {
            addDependency(dependencies, dep);
        }
        Element annotations = (Element) new Element("opm:Annotations", OPM_NS);
        root.appendChild(annotations);
        for (Annotation anno : graph.getAnnotations().getAnnotation()) {
            addAnnotation(annotations, anno);
        }
        return textify(new Document(root));
    }

    static protected void addAccount(final Element parent, final Account account) {
        Element accElt = (Element) new Element("opm:Account", OPM_NS);
        accElt.addAttribute(new Attribute("id", account.getId()));
        for (EmbeddedAnnotation ann : account.getAnnotation()) {
            addAnnotation(accElt, (Annotation)ann);
        }
        parent.appendChild(accElt);
    }

    static protected void addAccountElement(final Element parent, final Account account) {
        Element accElt = (Element) new Element("account");
        accElt.appendChild(account.getId());
        parent.appendChild(accElt);
    }

    static protected void addAgent(final Element parent, final Agent agent) {
        Element agtElt = (Element) new Element("opm:Agent", OPM_NS);
        agtElt.addAttribute(new Attribute("id", agent.getId()));
        for (Account acc : agent.getAccount()) {
            addAccountElement(agtElt, acc);
        }
        for (EmbeddedAnnotation ann : agent.getAnnotation()) {
            addAnnotation(agtElt, ann);
        }
        parent.appendChild(agtElt);
    }

    static protected void addProperty(final Element parent, final Property prop) {
        Element propElt = (Element) new Element("property");
        propElt.addAttribute(new Attribute("key", prop.getUri()));
        propElt.addAttribute(new Attribute("value", prop.getValue()));
        parent.appendChild(propElt);
    }

    static protected void addAnnotation(final Element parent, final EmbeddedAnnotation ann) {
        Element annElt = null;
        if (ann instanceof Annotation) {
            Annotation anno = (Annotation) ann;
            annElt = (Element) new Element("opm:Annotation", OPM_NS);
            annElt.addAttribute(new Attribute("id", anno.getId()));
            Element esElt = (Element) new Element("externalSubject");
            esElt.appendChild(anno.getExternalSubject());
            annElt.appendChild(esElt);
            Element lsElt = (Element) new Element("localSubject");
            lsElt.appendChild(anno.getLocalSubject());
            annElt.appendChild(lsElt);
            for (Property prop : anno.getPropertyList()) {
                addProperty(annElt, prop);
            }
            for (Account acc : anno.getAccount()) {
                addAccountElement(annElt, acc);
            }
        } else if (ann instanceof Label) {
            Label label = (Label)ann;
            annElt = (Element) new Element("label");
            annElt.addAttribute(new Attribute("value", label.getValue()));
        }
        parent.appendChild(annElt);
    }

    static protected void addArtifact(final Element parent, final Artifact artifact) {
        Element artElt = (Element) new Element("opm:Artifact", OPM_NS);
        artElt.addAttribute(new Attribute("id", artifact.getId()));
        artElt.addAttribute(new Attribute("value", artifact.getValue()));
        //Element valElt = (Element) new Element("label", OPM_NS);
        //valElt.addAttribute(new Attribute("value", artifact.getValue()));
        //artElt.appendChild(valElt);
        for (Account acc : artifact.getAccount()) {
            addAccountElement(artElt, acc);
        }
        for (EmbeddedAnnotation ann : artifact.getAnnotation()) {
            addAnnotation(artElt, ann);
        }
        parent.appendChild(artElt);
    }

    static protected void addProcess(final Element parent, final nl.biggrid.plier.opm.Process process) {
        Element proElt = (Element) new Element("opm:Process", OPM_NS);
        proElt.addAttribute(new Attribute("id", process.getId()));
        for (Account acc : process.getAccount()) {
            addAccountElement(proElt, acc);
        }
        for (EmbeddedAnnotation ann : process.getAnnotation()) {
            addAnnotation(proElt, ann);
        }
        parent.appendChild(proElt);
    }

    static protected void addDependency(final Element parent, final CausalDependency dep) {
        if (dep instanceof Used) {
            addUsed(parent, (Used) dep);
        } else if (dep instanceof WasControlledBy) {
            addWasControlledBy(parent, (WasControlledBy) dep);
        } else if (dep instanceof WasGeneratedBy) {
            addWasGeneratedBy(parent, (WasGeneratedBy) dep);
        } else if (dep instanceof WasDerivedFrom) {
            addWasDerivedFrom(parent, (WasDerivedFrom) dep);
        } else if (dep instanceof WasTriggeredBy) {
            addWasTriggeredBy(parent, (WasTriggeredBy) dep);
        }
    }

    static protected void addUsed(final Element parent, final Used used) {
        Element uElt = (Element) new Element("opm:Used", OPM_NS);
        Element effElt = (Element) new Element("opm:effect", OPM_NS);
        effElt.addAttribute(new Attribute("ref", used.getEffect().getId()));
        Element cozElt = (Element) new Element("opm:cause", OPM_NS);
        cozElt.addAttribute(new Attribute("ref", used.getCause().getId()));
        Element rolElt = (Element) new Element("opm:role", OPM_NS);
        rolElt.addAttribute(new Attribute("value", used.getRole()));
        uElt.appendChild(effElt);
        uElt.appendChild(cozElt);
        uElt.appendChild(rolElt);
        for (Account acc : used.getAccount()) {
            addAccountElement(uElt, acc);
        }
        parent.appendChild(uElt);
    }

    static protected void addWasControlledBy(final Element parent, final WasControlledBy wcb) {
        Element wcbElt = (Element) new Element("opm:WasControlledBy", OPM_NS);
        Element effElt = (Element) new Element("opm:effect", OPM_NS);
        effElt.addAttribute(new Attribute("ref", wcb.getEffect().getId()));
        Element cozElt = (Element) new Element("opm:cause", OPM_NS);
        cozElt.addAttribute(new Attribute("ref", wcb.getCause().getId()));
        Element rolElt = (Element) new Element("opm:role", OPM_NS);
        rolElt.addAttribute(new Attribute("value", wcb.getRole()));
        wcbElt.appendChild(effElt);
        wcbElt.appendChild(cozElt);
        wcbElt.appendChild(rolElt);
        for (Account acc : wcb.getAccount()) {
            addAccountElement(wcbElt, acc);
        }
        parent.appendChild(wcbElt);
    }

    static protected void addWasGeneratedBy(final Element parent, final WasGeneratedBy wgb) {
        Element wgbElt = (Element) new Element("opm:WasGeneratedBy", OPM_NS);
        Element effElt = (Element) new Element("opm:effect", OPM_NS);
        effElt.addAttribute(new Attribute("ref", wgb.getEffect().getId()));
        Element cozElt = (Element) new Element("opm:cause", OPM_NS);
        cozElt.addAttribute(new Attribute("ref", wgb.getCause().getId()));
        Element rolElt = (Element) new Element("opm:role", OPM_NS);
        rolElt.addAttribute(new Attribute("value", wgb.getRole()));
        wgbElt.appendChild(effElt);
        wgbElt.appendChild(cozElt);
        for (Account acc : wgb.getAccount()) {
            addAccountElement(wgbElt, acc);
        }
        wgbElt.appendChild(rolElt);
        parent.appendChild(wgbElt);
    }

    static protected void addWasDerivedFrom(final Element parent, final WasDerivedFrom wdf) {
        Element wdfElt = (Element) new Element("opm:WasDerivedFrom", OPM_NS);
        Element effElt = (Element) new Element("opm:effect", OPM_NS);
        effElt.addAttribute(new Attribute("ref", wdf.getEffect().getId()));
        Element cozElt = (Element) new Element("opm:cause", OPM_NS);
        cozElt.addAttribute(new Attribute("ref", wdf.getCause().getId()));
        wdfElt.appendChild(effElt);
        wdfElt.appendChild(cozElt);
        for (Account acc : wdf.getAccount()) {
            addAccountElement(wdfElt, acc);
        }
        parent.appendChild(wdfElt);
    }

    static protected void addWasTriggeredBy(final Element parent, final WasTriggeredBy wtb) {
        Element wtbElt = (Element) new Element("opm:WasTriggeredBy", OPM_NS);
        Element effElt = (Element) new Element("opm:effect", OPM_NS);
        effElt.addAttribute(new Attribute("ref", wtb.getEffect().getId()));
        Element cozElt = (Element) new Element("opm:cause", OPM_NS);
        cozElt.addAttribute(new Attribute("ref", wtb.getCause().getId()));
        wtbElt.appendChild(effElt);
        wtbElt.appendChild(cozElt);
        for (Account acc : wtb.getAccount()) {
            addAccountElement(wtbElt, acc);
        }
        parent.appendChild(wtbElt);
    }

    static protected String textify(final Document doc) {
        String xml = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Serializer serializer = new Serializer(baos, CHAR_ENCODING);
            serializer.setIndent(4);
            serializer.write(doc);
            xml = baos.toString(CHAR_ENCODING);
        } catch (UnsupportedEncodingException uce) {
            System.err.println(uce);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return xml;
    }

    static protected OPMGraph fromXML(final String xmlGraph) {
        OPMGraph opmGraph = new OPMGraph();
        Builder builder = new Builder();
        try {
            Document doc = builder.build(xmlGraph, null);
            Element root = doc.getRootElement();            
            if ("opm:OPMGraph".equalsIgnoreCase(root.getQualifiedName())) {
                opmGraph.setId(root.getAttributeValue("id"));
            }
            Elements children = root.getChildElements();
            for (int i=0; i<children.size(); i++) {
                Element child = children.get(i);
                String eltName = child.getQualifiedName();
                if ("opm:Accounts".equalsIgnoreCase(eltName)) {
                    setAccounts(child, opmGraph);
                } else if ("opm:Agents".equalsIgnoreCase(eltName)) {
                    setAgents(child, opmGraph);
                } else if ("opm:Artifacts".equalsIgnoreCase(eltName)) {
                    setArtifacts(child, opmGraph);
                } else if ("opm:Processes".equalsIgnoreCase(eltName)) {
                    setProcesses(child, opmGraph);
                } else if ("opm:CausalDependencies".equalsIgnoreCase(eltName)) {
                    setCausalDependencies(child, opmGraph);
                } else if ("opm:Annotations".equalsIgnoreCase(eltName)) {
                    setAnnotations(child, opmGraph);
                }
            }
        } catch (ParsingException ex) { // Indicates a well-formedness error
            System.out.println("The graph XML is not well-formed.");
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return opmGraph;
    }

    static protected void setAccounts(Element eltAccounts, OPMGraph graph) {
        Accounts accounts = new Accounts();
        Elements children = eltAccounts.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i);
            String eltName = child.getQualifiedName();
            if ("opm:Account".equalsIgnoreCase(eltName)) {
                Account account = new Account();
                account.setId(children.get(i).getAttributeValue("id"));
                accounts.getAccount().add(account);
            }
        }
        graph.setAccounts(accounts);
    }

    static protected Account getAccountById(String aid, OPMGraph graph) {
        for (Account acc : graph.getAccounts().getAccount()) {
            if (aid.equalsIgnoreCase(acc.getId())) {
                return acc;
            }
        }
        return null;
    }

    static protected void setNodeEdgeAccounts(Element element, HasAccounts nodeEdge, OPMGraph graph) {
        Elements children = element.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i);
            String eltName = child.getQualifiedName();
            if ("account".equalsIgnoreCase(eltName)) {
                nodeEdge.getAccount().add(getAccountById(child.getValue(), graph));
            }
        }
    }

    static protected EmbeddedAnnotation getAnnotation(Element element) {
        Annotation ann = new Annotation();
        ann.setId(element.getAttributeValue("id"));
        Elements children = element.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i);
            String eltName = child.getQualifiedName();
            if ("localSubject".equalsIgnoreCase(eltName)) {
                ann.setLocalSubject(child.getValue());
            }
            if ("externalSubject".equalsIgnoreCase(eltName)) {
                ann.setExternalSubject(child.getValue());
            }
            if ("property".equalsIgnoreCase(eltName)) {
                Property prop = new Property();
                prop.setUri(child.getAttributeValue("key"));
                prop.setValue(child.getAttributeValue("value"));
                ann.getPropertyList().add(prop);
            }
        }
        return ann;
    }

    static protected void setNodeEdgeAnnotations(Element element, Annotable annotable) {
        Elements children = element.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i);
            String eltName = child.getQualifiedName();
            if ("opm:Annotation".equalsIgnoreCase(eltName)) {
                annotable.getAnnotation().add(getAnnotation(child));
            }
        }
    }

    static protected void setAgents(Element eltAgents, OPMGraph graph) {
        Agents agents = new Agents();
        Elements children = eltAgents.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i);
            String eltName = child.getQualifiedName();
            if ("opm:Agent".equalsIgnoreCase(eltName)) {
                Agent agent = new Agent();
                agent.setId(child.getAttributeValue("id"));
                agents.getAgent().add(agent);
                setNodeEdgeAccounts(child, agent, graph);
                setNodeEdgeAnnotations(child, agent);
            }
        }
        graph.setAgents(agents);
    }

    static protected void setArtifacts(Element eltArtifactts, OPMGraph graph) {
        Artifacts artifacts = new Artifacts();
        Elements children = eltArtifactts.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i);
            String eltName = child.getQualifiedName();
            if ("opm:Artifact".equalsIgnoreCase(eltName)) {
                Artifact artifact = new Artifact();
                artifact.setId(children.get(i).getAttributeValue("id"));
                artifact.setValue(children.get(i).getAttributeValue("value"));
                artifacts.getArtifact().add(artifact);
                setNodeEdgeAccounts(child, artifact, graph);
                setNodeEdgeAnnotations(child, artifact);
            }
        }
        graph.setArtifacts(artifacts);
    }

    static protected void setProcesses(Element eltProcesses, OPMGraph graph) {
        Processes processes = new Processes();
        Elements children = eltProcesses.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i);
            String eltName = child.getQualifiedName();
            if ("opm:Process".equalsIgnoreCase(eltName)) {
                nl.biggrid.plier.opm.Process process = new nl.biggrid.plier.opm.Process();
                process.setId(children.get(i).getAttributeValue("id"));
                processes.getProcess().add(process);
                setNodeEdgeAccounts(child, process, graph);
                setNodeEdgeAnnotations(child, process);
            }
        }
        graph.setProcesses(processes);
    }

    static protected nl.biggrid.plier.opm.Process getGraphProcessById(OPMGraph graph, String pid) {
        for (nl.biggrid.plier.opm.Process process : graph.getProcesses().getProcess()) {
            if (pid.equalsIgnoreCase(process.getId())) {
                return process;
            }
        }
        return null;
    }

    static protected Artifact getGraphArtifactById(OPMGraph graph, String aid) {
        for (Artifact artifact : graph.getArtifacts().getArtifact()) {
            if (aid.equalsIgnoreCase(artifact.getId())) {
                return artifact;
            }
        }
        return null;
    }

    static protected Agent getGraphAgentById(OPMGraph graph, String aid) {
        for (Agent agent : graph.getAgents().getAgent()) {
            if (aid.equalsIgnoreCase(agent.getId())) {
                return agent;
            }
        }
        return null;
    }

    static protected String getCDEffect(Element cd) {
        Elements children = cd.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            if ("opm:effect".equalsIgnoreCase(children.get(i).getQualifiedName())) {
                return children.get(i).getAttributeValue("ref");
            }
        }
        return null;
    }

    static protected String getCDCause(Element cd) {
        Elements children = cd.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            if ("opm:cause".equalsIgnoreCase(children.get(i).getQualifiedName())) {
                return children.get(i).getAttributeValue("ref");
            }
        }
        return null;
    }

    static protected String getCDRole(Element cd) {
        Elements children = cd.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            if ("opm:role".equalsIgnoreCase(children.get(i).getQualifiedName())) {
                return children.get(i).getAttributeValue("value");
            }
        }
        return null;
    }

    static protected void setCausalDependencies(Element eltCDs, OPMGraph graph) {
        CausalDependencies cds = new CausalDependencies();
        Elements children = eltCDs.getChildElements();
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i);
            String eltName = child.getQualifiedName();
            if ("opm:Used".equalsIgnoreCase(eltName)) {
                Used used = new Used();
                used.setEffect(getGraphProcessById(graph, getCDEffect(child)));
                used.setCause(getGraphArtifactById(graph, getCDCause(child)));
                used.setRole(getCDRole(child));
                setNodeEdgeAccounts(child, used, graph);
                setNodeEdgeAnnotations(child, used);
                cds.getDependency().add(used);
            } else if ("opm:WasGeneratedBy".equalsIgnoreCase(eltName)) {
                WasGeneratedBy wgb = new WasGeneratedBy();
                wgb.setEffect(getGraphArtifactById(graph, getCDEffect(child)));
                wgb.setCause(getGraphProcessById(graph, getCDCause(child)));
                wgb.setRole(getCDRole(child));
                setNodeEdgeAccounts(child, wgb, graph);
                setNodeEdgeAnnotations(child, wgb);
                cds.getDependency().add(wgb);
            } else if ("opm:WasControlledBy".equalsIgnoreCase(eltName)) {
                WasControlledBy wcb = new WasControlledBy();
                wcb.setEffect(getGraphProcessById(graph, getCDEffect(child)));
                wcb.setCause(getGraphAgentById(graph, getCDCause(child)));
                wcb.setRole(getCDRole(child));
                setNodeEdgeAccounts(child, wcb, graph);
                setNodeEdgeAnnotations(child, wcb);
                cds.getDependency().add(wcb);
            } else if ("opm:WasDerivedFrom".equalsIgnoreCase(eltName)) {
                WasDerivedFrom wdf = new WasDerivedFrom();
                wdf.setEffect(getGraphArtifactById(graph, getCDEffect(child)));
                wdf.setCause(getGraphArtifactById(graph, getCDCause(child)));
                setNodeEdgeAccounts(child, wdf, graph);
                setNodeEdgeAnnotations(child, wdf);
                cds.getDependency().add(wdf);
            } else if ("opm:WasTriggeredBy".equalsIgnoreCase(eltName)) {
                WasTriggeredBy wtb = new WasTriggeredBy();
                wtb.setEffect(getGraphProcessById(graph, getCDEffect(child)));
                wtb.setCause(getGraphProcessById(graph, getCDCause(child)));
                setNodeEdgeAccounts(child, wtb, graph);
                setNodeEdgeAnnotations(child, wtb);
                cds.getDependency().add(wtb);
            }
        }
        graph.setCausalDependencies(cds);
    }

    static protected void setAnnotations(Element eltAnnos, OPMGraph graph) {
        Annotations annos = new Annotations();
        Elements children = eltAnnos.getChildElements("opm:Annotation");
        for (int i=0; i<children.size(); i++) {
            Annotation anno = new Annotation();
            anno.setId(children.get(i).getAttributeValue("id"));
            annos.getAnnotation().add(anno);
        }
        graph.setAnnotations(annos);
    }

}
