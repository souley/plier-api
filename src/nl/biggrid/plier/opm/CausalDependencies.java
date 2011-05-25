//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.11 at 11:17:38 AM CEST 
//


package nl.biggrid.plier.opm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CausalDependencies complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CausalDependencies">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="used" type="{http://openprovenance.org/model/v1.1.a}Used"/>
 *           &lt;element name="wasGeneratedBy" type="{http://openprovenance.org/model/v1.1.a}WasGeneratedBy"/>
 *           &lt;element name="wasTriggeredBy" type="{http://openprovenance.org/model/v1.1.a}WasTriggeredBy"/>
 *           &lt;element name="wasDerivedFrom" type="{http://openprovenance.org/model/v1.1.a}WasDerivedFrom"/>
 *           &lt;element name="wasControlledBy" type="{http://openprovenance.org/model/v1.1.a}WasControlledBy"/>
 *           &lt;element name="used_" type="{http://openprovenance.org/model/v1.1.a}UsedStar"/>
 *           &lt;element name="wasGeneratedBy_" type="{http://openprovenance.org/model/v1.1.a}WasGeneratedByStar"/>
 *           &lt;element name="wasTriggeredBy_" type="{http://openprovenance.org/model/v1.1.a}WasTriggeredByStar"/>
 *           &lt;element name="wasDerivedFrom_" type="{http://openprovenance.org/model/v1.1.a}WasDerivedFromStar"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CausalDependencies", propOrder = {
    "dependency"
})
public class CausalDependencies {

    @XmlElements({
        @XmlElement(name = "used_", type = UsedStar.class),
        @XmlElement(name = "wasControlledBy", type = WasControlledBy.class),
        @XmlElement(name = "wasGeneratedBy", type = WasGeneratedBy.class),
        @XmlElement(name = "wasDerivedFrom_", type = WasDerivedFromStar.class),
        @XmlElement(name = "wasTriggeredBy_", type = WasTriggeredByStar.class),
        @XmlElement(name = "wasGeneratedBy_", type = WasGeneratedByStar.class),
        @XmlElement(name = "wasTriggeredBy", type = WasTriggeredBy.class),
        @XmlElement(name = "wasDerivedFrom", type = WasDerivedFrom.class),
        @XmlElement(name = "used", type = Used.class)
    })
    protected List<CausalDependency> dependency;

    /**
     * Gets the value of the usedOrWasGeneratedByOrWasTriggeredBy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the usedOrWasGeneratedByOrWasTriggeredBy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsedOrWasGeneratedByOrWasTriggeredBy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UsedStar }
     * {@link WasControlledBy }
     * {@link WasGeneratedBy }
     * {@link WasDerivedFromStar }
     * {@link WasTriggeredByStar }
     * {@link WasGeneratedByStar }
     * {@link WasTriggeredBy }
     * {@link WasDerivedFrom }
     * {@link Used }
     * 
     * 
     */
    public List<CausalDependency> getDependency() {
        if (dependency == null) {
            dependency = new ArrayList<CausalDependency>();
        }
        return this.dependency;
    }

}