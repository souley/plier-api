//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.11 at 11:17:38 AM CEST 
//


package nl.biggrid.plier.opm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 
 *             Java class for OPMGraph complex type. See <A href="http://twiki.ipaw.info/bin/view/Challenge/OPM1-01Review">OPMGraph</A>.
 *          
 * 
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OPMGraph", propOrder = {
    "accounts",
    "processes",
    "artifacts",
    "agents",
    "causalDependencies",
    "annotations",
    "annotation"
})
public class OPMGraph {

    protected Accounts accounts;
    protected Processes processes;
    protected Artifacts artifacts;
    protected Agents agents;
    protected CausalDependencies causalDependencies;
    protected Annotations annotations;
    @XmlElementRef(name = "annotation", namespace = "http://openprovenance.org/model/v1.1.a", type = JAXBElement.class)
    protected List<EmbeddedAnnotation> annotation;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    protected long dbId;
    /**
     * Gets the value of the accounts property.
     * 
     * @return
     *     possible object is
     *     {@link Accounts }
     *     
     */
    public Accounts getAccounts() {
        return accounts;
    }

    /**
     * Sets the value of the accounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Accounts }
     *     
     */
    public void setAccounts(Accounts value) {
        this.accounts = value;
    }

    /**
     * Gets the value of the processes property.
     * 
     * @return
     *     possible object is
     *     {@link Processes }
     *     
     */
    public Processes getProcesses() {
        return processes;
    }

    /**
     * Sets the value of the processes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Processes }
     *     
     */
    public void setProcesses(Processes value) {
        this.processes = value;
    }

    /**
     * Gets the value of the artifacts property.
     * 
     * @return
     *     possible object is
     *     {@link Artifacts }
     *     
     */
    public Artifacts getArtifacts() {
        return artifacts;
    }

    /**
     * Sets the value of the artifacts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Artifacts }
     *     
     */
    public void setArtifacts(Artifacts value) {
        this.artifacts = value;
    }

    /**
     * Gets the value of the agents property.
     * 
     * @return
     *     possible object is
     *     {@link Agents }
     *     
     */
    public Agents getAgents() {
        return agents;
    }

    /**
     * Sets the value of the agents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Agents }
     *     
     */
    public void setAgents(Agents value) {
        this.agents = value;
    }

    /**
     * Gets the value of the causalDependencies property.
     * 
     * @return
     *     possible object is
     *     {@link CausalDependencies }
     *     
     */
    public CausalDependencies getCausalDependencies() {
        return causalDependencies;
    }

    /**
     * Sets the value of the causalDependencies property.
     * 
     * @param value
     *     allowed object is
     *     {@link CausalDependencies }
     *     
     */
    public void setCausalDependencies(CausalDependencies value) {
        this.causalDependencies = value;
    }

    /**
     * Gets the value of the annotations property.
     * 
     * @return
     *     possible object is
     *     {@link Annotations }
     *     
     */
    public Annotations getAnnotations() {
        return annotations;
    }

    /**
     * Sets the value of the annotations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Annotations }
     *     
     */
    public void setAnnotations(Annotations value) {
        this.annotations = value;
    }

    /**
     * Gets the value of the annotation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the annotation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnnotation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Value }{@code >}
     * {@link JAXBElement }{@code <}{@link Label }{@code >}
     * {@link JAXBElement }{@code <}{@link Profile }{@code >}
     * {@link JAXBElement }{@code <}{@link EmbeddedAnnotation }{@code >}
     * {@link JAXBElement }{@code <}{@link PName }{@code >}
     * {@link JAXBElement }{@code <}{@link Type }{@code >}
     * 
     * 
     */
    public List<EmbeddedAnnotation> getAnnotation() {
        if (annotation == null) {
            annotation = new ArrayList<EmbeddedAnnotation>();
        }
        return this.annotation;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
