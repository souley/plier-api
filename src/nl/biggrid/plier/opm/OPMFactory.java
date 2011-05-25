/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.biggrid.plier.opm;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Souley
 */
public class OPMFactory {
    public Account newAccount(String id) {
        Account res = new Account();
        res.setId(id);
        return res;
    }

    public Account newAccount(String id, String label) {
        Account res = new Account();
        res.setId(id);
        res.getAnnotation().add(newLabel(label));
        return res;
    }


    public void addAccounts(HasAccounts element, Collection<Account> accounts, Object ignoreForErasure) {
        if ((accounts !=null) && (accounts.size()!=0)) {
            LinkedList ll=new LinkedList();
            for (Account acc: accounts) {
                //ll.add(newAccountRef(acc));
                ll.add(acc);
            }
            addAccounts(element,ll);
        }
    }
    public void addAccounts(HasAccounts element, Collection<Account> accounts) {
        if ((accounts !=null) && (accounts.size()!=0)) {
            element.getAccount().addAll(accounts);
        }
    }
    
    public Label newLabel(String label) {
        //System.out.println("### factory::newLabel for "+label);
        Label res = new Label();
        res.setId(label); //sm
        res.setValue(label);
        return res;
    }

    public void addAnnotation(Annotable annotable, Label ann) {
        if (ann!=null) {
            //System.out.println("### factory::addAnnotation for label "+ann.getValue());
            //annotable.getAnnotation().add(newLabel(ann.getValue()));
            annotable.getAnnotation().add(ann);
        }
    }

    public void addProperty(Annotation ann, Property p) {
        ann.getPropertyList().add(p);
    }

    public void addProperty(Annotation ann, List<Property> p) {
        ann.getPropertyList().addAll(p);
    }

    public void addProperty(EmbeddedAnnotation ann, Property p) {
        ann.getPropertyList().add(p);
    }

    public void addProperty(EmbeddedAnnotation ann, List<Property> p) {
        ann.getPropertyList().addAll(p);
    }

    public Property newProperty(String property,
                                String value) {
        Property res = new Property();
        res.setUri(property);
        res.setValue(value);
        return res;
    }

    public Property newProperty(Property property) {
        return newProperty(property.getUri(),property.getValue());
    }

    public EmbeddedAnnotation newEmbeddedAnnotation(String id,
                                                    String property,
                                                    String value,
                                                    Collection<Account> accs) {
        LinkedList<Account> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                //ll.add(newAccountRef(acc)); //sm
                ll.add(acc); //sm
            }
        }
        return newEmbeddedAnnotation(id,property,value,ll,null);
    }

    public EmbeddedAnnotation newEmbeddedAnnotation(String id,
                                                    String property,
                                                    String value,
                                                    Collection<Account> accs,
                                                    Object dummyParameterForAvoidingSameErasure) {
        EmbeddedAnnotation res = new EmbeddedAnnotation();
        res.setId(id);
        addProperty(res,newProperty(property, value));
        addAccounts(res, accs);
        return res;
    }

    public EmbeddedAnnotation newEmbeddedAnnotation(String id,
                                                    List<Property> properties,
                                                    Collection<Account> accs,
                                                    Object dummyParameterForAvoidingSameErasure) {
        EmbeddedAnnotation res = new EmbeddedAnnotation();
        res.setId(id);
        if (properties!=null) {
            addProperty(res,properties);
        }
        addAccounts(res, accs);
        return res;
    }

    public Annotation newAnnotation(String id,
                                    String o,
                                    List<Property> properties,
                                    Collection<Account> accs) {
        Annotation res = new Annotation();
        res.setId(id);
        res.setLocalSubject(o);
        for (Property property: properties) {
            addProperty(res,property);
        }
        addAccounts(res,accs);
        return res;
    }

    public Annotation newAnnotation(Annotation ann) {
        Annotation res=newAnnotation(ann.getId(),
                                     ann.getLocalSubject(),
                                     ann.getPropertyList(),
                                     ann.getAccount());
        return res;
    }


    public void addAnnotation(Annotable annotable, EmbeddedAnnotation ann) {
        annotable.getAnnotation().add(ann);
    }

    public void addNewAnnotations(Annotable res,
                                  List<EmbeddedAnnotation> anns) {
        for (EmbeddedAnnotation ann: anns) {
            //EmbeddedAnnotation ea = ann.getValue(); // sm
            if (ann.getId()!=null)
            addAnnotation(res, newEmbeddedAnnotation(ann.getId(),
                                                     ann.getPropertyList(),
                                                     ann.getAccount(),
                                                     null));
        }
    }

    public Agent newAgent(Agent a) {
        LinkedList<Account> ll = new LinkedList();
//        for (Account acc: a.getAccount()) {
//            ll.add(newAccount(acc));
//        }
//        Agent res = newAgent(a.getId(),ll);
        Agent res = newAgent(a.getId(),a.getAccount());
        addNewAnnotations(res, a.getAnnotation());
        return res;
    }

    public Agent newAgent(String ag,
                          Collection<Account> accounts) {
        return newAgent(ag, accounts, null);
    }

    public Agent newAgent(String ag,
                          Collection<Account> accounts,
                          String label) {        
        Agent res = new Agent();
        res.setId(ag);
        addAccounts(res, accounts, null);
        if (label!=null) {
            addAnnotation(res, newLabel(label));
        }
        return res;
    }

    public Process newProcess(String pr,
                              Collection<Account> accounts) {
        return newProcess(pr,accounts,null);
    }

    public Process newProcess(String pr,
                              Collection<Account> accounts,
                              String label) {
        Process res = new Process();
        res.setId(pr);
        addAccounts(res, accounts, null);
        if (label!=null) {
            addAnnotation(res, newLabel(label));
        }
        return res;
    }

    public Artifact newArtifact(String id,
                                Collection<Account> accounts) {
        return newArtifact(id,accounts,null);
    }

    public Artifact newArtifact(String id,
                                Collection<Account> accounts,
                                String label) {
        Artifact res = new Artifact();
        res.setId(id);
        addAccounts(res,accounts,null);
        if (label!=null) {
            addAnnotation(res, newLabel(label));
        }
        return res;
    }

    public Role newRole(String value) {
        return newRole(value, value);
    }

    public Role newRole(Role role) {
        return newRole(role.getId(),role.getValue());
    }

    public Role newRole(String id, String value) {
        Role res = new Role();
        res.setId(id);
        res.setValue(value);
        return res;
    }

    public Used newUsed(Process p,
                        String role,
                        Artifact a,
                        Collection<Account> accounts) {
        Used res = newUsed(null,p,role,a,accounts);
        return res;
    }

    public Used newUsed(String id,
                        Process pid,
                        String role,
                        Artifact aid,
                        Collection<Account> accounts) {
        Used res= new Used();
        res.setId(id);
        res.setEffect(pid);
        res.setRole(role);
        res.setCause(aid);
        addAccounts(res,accounts);
        return res;
    }

    public WasGeneratedBy newWasGeneratedBy(Artifact a,
                                            String role,
                                            Process p,
                                            Collection<Account> accounts) {
        return newWasGeneratedBy(null,a,role,p,accounts);
    }

    public WasGeneratedBy newWasGeneratedBy(String id,
                                            Artifact aid,
                                            String role,
                                            Process pid,
                                            Collection<Account> accounts) {
        WasGeneratedBy res = new WasGeneratedBy();
        res.setId(id);
        res.setCause(pid);
        res.setRole(role);
        res.setEffect(aid);
        addAccounts(res,accounts);
        return res;
    }

    public WasControlledBy newWasControlledBy(Process pid,
                                              String role,
                                              Agent agid,
                                              Collection<Account> accounts) {
        return newWasControlledBy(null,pid,role,agid,accounts);
    }

    public WasControlledBy newWasControlledBy(String id,
                                              Process pid,
                                              String role,
                                              Agent agid,
                                              Collection<Account> accounts) {
        WasControlledBy res = new WasControlledBy();
        res.setId(id);
        res.setEffect(pid);
        res.setRole(role);
        res.setCause(agid);
        addAccounts(res,accounts);
        return res;
    }

    public OPMGraph newOPMGraph(String id,
                                Collection<Account> accs,
                                Overlaps [] ovs,
                                Process [] ps,
                                Artifact [] as,
                                Agent [] ags,
                                CausalDependency [] lks,
                                Annotation [] anns)
    {

        return newOPMGraph(id,
                           accs,
                           ((ovs==null) ? null : Arrays.asList(ovs)),
                           ((ps==null) ? null : Arrays.asList(ps)),
                           ((as==null) ? null : Arrays.asList(as)),
                           ((ags==null) ? null : Arrays.asList(ags)),
                           ((lks==null) ? null : Arrays.asList(lks)),
                           ((anns==null) ? null : Arrays.asList(anns)));
    }

    public OPMGraph newOPMGraph(String id,
                                Collection<Account> accs,
                                Collection<Overlaps> ops,
                                Collection<Process> ps,
                                Collection<Artifact> as,
                                Collection<Agent> ags,
                                Collection<CausalDependency> lks,
                                Collection<Annotation> anns)
    {
        OPMGraph res = new OPMGraph();
        res.setId(id);
        if (accs!=null) {
            Accounts aaccs = new Accounts();
            aaccs.getAccount().addAll(accs);
            if (ops!=null && ops.size()>0)
                aaccs.getOverlaps().addAll(ops);
            res.setAccounts(aaccs);

        }
        if (ps!=null) {
            Processes pps = new Processes();
            pps.getProcess().addAll(ps);
            res.setProcesses(pps);
        }
        if (as!=null) {
            Artifacts aas = new Artifacts();
            aas.getArtifact().addAll(as);
            res.setArtifacts(aas);
        }
        if (ags!=null) {
            Agents aags = new Agents();
            aags.getAgent().addAll(ags);
            res.setAgents(aags);
        }
        if (lks!=null) {
            CausalDependencies ccls = new CausalDependencies();
            ccls.getDependency().addAll(lks);
            res.setCausalDependencies(ccls);
        }
        if (anns!=null) {
            Annotations l = new Annotations();
            l.getAnnotation().addAll(anns);
            res.setAnnotations(l);
        }
        return res;
    }

}
